package quanphung.hust.nctnbackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.OrderSpecifier;
import lombok.extern.slf4j.Slf4j;
import quanphung.hust.nctnbackend.domain.BidInfo;
import quanphung.hust.nctnbackend.domain.LikedItem;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.filter.LotFilter;
import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;
import quanphung.hust.nctnbackend.dto.request.GetLotRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.dto.response.GetLotResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateBidResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateLotResponse;
import quanphung.hust.nctnbackend.dto.sse.BidMessage;
import quanphung.hust.nctnbackend.exception.InvalidSortColumnException;
import quanphung.hust.nctnbackend.exception.InvalidSortOrderException;
import quanphung.hust.nctnbackend.mapping.LotMapping;
import quanphung.hust.nctnbackend.repository.BidInfoRepository;
import quanphung.hust.nctnbackend.repository.LikedItemRepository;
import quanphung.hust.nctnbackend.repository.LotInfoRepository;
import quanphung.hust.nctnbackend.repository.orderutils.LotOrderUtils;
import quanphung.hust.nctnbackend.utils.SecurityUtils;

@Service
@Slf4j
public class LotInfoServiceImpl implements LotInfoService
{
  @Autowired
  private LotInfoRepository lotInfoRepository;

  @Autowired
  private LotMapping mapping;

  @Autowired
  private SseService sseService;

  @Autowired
  private BidInfoRepository bidInfoRepository;

  @Autowired
  private LikedItemRepository likedItemRepository;

  @Override
  public void createLotInfo(CreateLotInfoRequest request)
  {
    LotInfo item = convertRequest(request);
    lotInfoRepository.save(item);
  }

  private LotInfo convertRequest(CreateLotInfoRequest request)
  {
    return LotInfo.builder()
      .name(request.getName())
      .description(request.getDescription())
      .step(request.getStep())
      .estmPrice(request.getEstmPrice())
      .currentPrice(request.getCurrentPrice())
      .initPrice(request.getCurrentPrice())
      .imageUrl(request.getImageUrl())
      .build();
  }

  @Override
  public void updateLotInfo(UpdateItemRequest request)
  {

  }

  @Override
  public LotInfoDto getLotDetail(Long id)
  {
    Optional<LotInfo> lotOpt = lotInfoRepository.findById(id);
    if (lotOpt.isPresent())
    {
      LotInfo lot = lotOpt.get();
      return mapping.convertToDto(lot);
    }
    else
    {
      log.error("Id not available!");
      throw new BadRequestException(("Id not available!"));
    }

  }

  @Override
  @Transactional
  public GetLotResponse getLotInfos(GetLotRequest request)
  {
    Integer page = request.getPage();
    Integer size = request.getSize();
    LotFilter filter = LotFilter.builder()
      .name(request.getName())
      .isSold(request.getIsSold())
      .orderInSession(request.getOrderInSession())
      .maxPrice(request.getMaxPrice())
      .minPrice(request.getMinPrice())
      .session(request.getSession())
      .build();
    GetLotResponse response;
    try
    {
      OrderSpecifier<String>[] orderSpecifiers = LotOrderUtils.createOrderBy(request.getOrderByColumns());
      List<LotInfo> lotInfoList = lotInfoRepository.findLotInfoByPagingAndFilter(size, page, filter, orderSpecifiers);
      List<LotInfoDto> lotInfoDtoList = lotInfoList.stream()
        .map(item -> mapping.convertToDto(item))
        .collect(Collectors.toList());
      Long count = lotInfoRepository.countLotInfoByPagingAndFilter(size, page, filter);
      response = GetLotResponse.builder()
        .lotInfoDtoList(lotInfoDtoList)
        .count(count)
        .build();

    }
    catch (InvalidSortColumnException | InvalidSortOrderException e)
    {
      log.error(e.getMessage());
      throw new BadRequestException("Invalid sort column!");
    }
    return response;
  }

  @Override
  @Transactional
  public ManipulateLotResponse markSold(BidRequest request)
  {
    ObjectMapper objectMapper = new ObjectMapper();
    ManipulateLotResponse response = new ManipulateLotResponse();

    Long lotId = request.getLotId();
    Long soldPrice = request.getBidPrice();
    String owner = request.getUser();
    Optional<LotInfo> lotOpt = lotInfoRepository.findById(lotId);
    if (lotOpt.isPresent())
    {
      LotInfo lot = lotOpt.get();

      lot.setSold(true);
      lot.setSoldPrice(soldPrice);
      lot.setSoldFor(owner);
      lot = lotInfoRepository.save(lot);

      Optional<BidInfo> bidOptional = bidInfoRepository.findById(request.getBidId());
      if(bidOptional.isPresent())
      {
        BidInfo bidInfo = bidOptional.get();
        bidInfo.setStatus("won");

        bidInfo = bidInfoRepository.save(bidInfo);
      }
      sseService.sendNotificationToAll("auction-update","Lot update");

      BidMessage message = BidMessage.builder()
        .owner(owner)
        .price(soldPrice)
        .name(lot.getName())
        .build();
      try
      {
        sseService.sendNotificationToAll("sold-for",objectMapper.writeValueAsString(message));
      }
      catch (JsonProcessingException e)
      {
        log.warn(e.getMessage());
      }
      response.setMessage("Mark sold successful!");
    }
    else
    {
      response.setError("Mark sold failed!");
    }
    return response;
  }

  @Override
  public ManipulateLotResponse add2Favorite(Long id)
  {

    ManipulateLotResponse response = new ManipulateLotResponse();
    Optional<LotInfo> lotOpt = lotInfoRepository.findById(id);
    String user = SecurityUtils.getCurrentUsername().orElse("system");
    if (lotOpt.isPresent())
    {
      LotInfo lot = lotOpt.get();

      Optional<LikedItem>  likedItemOpt = likedItemRepository.findLikedItemByCreatedByAndAndLotInfo(user,lot);
      if(likedItemOpt.isPresent())
      {
        LikedItem likedItem = likedItemOpt.get();

        likedItemRepository.delete(likedItem);
        response.setMessage("Remove from favorite success!");
      }else
      {
        LikedItem item = LikedItem.builder()
          .lotInfo(lot)
          .build();
        item = likedItemRepository.save(item);
        response.setMessage("Add to favorite success!");
      }
    }else
    {
      response.setMessage("Add to favorite failed!");
    }
    return response;
  }
}
