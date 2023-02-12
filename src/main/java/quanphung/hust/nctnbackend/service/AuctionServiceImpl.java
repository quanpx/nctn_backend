package quanphung.hust.nctnbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.OrderSpecifier;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.domain.UserAuction;
import quanphung.hust.nctnbackend.dto.AuctionDTO;
import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.UserAuctionDto;
import quanphung.hust.nctnbackend.dto.filter.AuctionFilter;
import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.GetAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.dto.response.AuctionDetailResponse;
import quanphung.hust.nctnbackend.dto.response.GetAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.GetUserAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateAuctionResponse;
import quanphung.hust.nctnbackend.dto.sse.AuctionMessage;
import quanphung.hust.nctnbackend.exception.InvalidSortColumnException;
import quanphung.hust.nctnbackend.exception.InvalidSortOrderException;
import quanphung.hust.nctnbackend.mapping.AuctionMapping;
import quanphung.hust.nctnbackend.mapping.LotMapping;
import quanphung.hust.nctnbackend.mapping.UserAuctionMapping;
import quanphung.hust.nctnbackend.repository.AuctionSessionRepository;
import quanphung.hust.nctnbackend.repository.BidInfoRepository;
import quanphung.hust.nctnbackend.repository.LotInfoRepository;
import quanphung.hust.nctnbackend.repository.UserAuctionRepository;
import quanphung.hust.nctnbackend.repository.orderutils.AuctionOrderUtils;
import quanphung.hust.nctnbackend.type.SessionStatus;
import quanphung.hust.nctnbackend.utils.SecurityUtils;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuctionServiceImpl implements AuctionService
{
  private static final String SYSTEM_NAME = "nctn_system";
  @Autowired
  private AuctionMapping mapping;

  @Autowired
  private LotMapping lotMapping;

  @Autowired
  private UserAuctionMapping userAuctionMapping;

  @Autowired
  private LotInfoRepository lotInfoRepository;

  @Autowired
  private AuctionSessionRepository auctionSessionRepository;

  @Autowired
  private UserAuctionRepository userAuctionRepository;

  @Autowired
  private BidInfoRepository bidInfoRepository;

  @Autowired
  private SseService sseService;

  @Override
  @Transactional
  public void createAuction(CreateAuctionRequest request)
  {
    List<Long> itemIds = request.getItemIds();
    List<LotInfo> items = lotInfoRepository.findAllById(itemIds);

    AuctionSession session = AuctionSession.builder()
      .name(request.getName())
      .description(request.getDescription())
      .startTime(new Timestamp(request.getStartTime()))
      .itemsInSession(items)
      .isStream(request.isStream())
      .streamLink(request.getStreamLink())
      .status(SessionStatus.CREATED.getStatus())
      .imageUrl(request.getImageUrl())
      .currLot(1)
      .nextLot(2)
      .numItem(items.size())
      .build();

    auctionSessionRepository.save(session);

    updateItemInSession(session, items);

  }

  @Override
  public GetAuctionResponse getAuctions(GetAuctionRequest request)
  {
    Integer page = request.getPage();
    Integer size = request.getSize();
    GetAuctionResponse response;
    AuctionFilter filter = AuctionFilter.builder()
      .createdAt(request.getCreatedAt())
      .startTime(request.getStartTime())
      .status(request.getStatus())
      .build();
    try
    {
      OrderSpecifier<String>[] orderSpecifiers = AuctionOrderUtils.createOrderBy(request.getOrderByColumns());
      List<AuctionSession> auctionSessions = auctionSessionRepository.findAuctionByPagingAndFilter(size, page, filter,
        orderSpecifiers);
      List<AuctionDTO> auctionDTOList = auctionSessions.stream()
        .map(item -> mapping.convertToDto(item))
        .collect(Collectors.toList());
      Long count = auctionSessionRepository.countAuctionByPagingAndFilter(page, size, filter);

      response = GetAuctionResponse.builder()
        .count(count)
        .auctionDTOList(auctionDTOList)
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
  public ManipulateAuctionResponse registerAuction(Long auctionSession)
  {
    ManipulateAuctionResponse response = new ManipulateAuctionResponse();

    Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(auctionSession);
    if (auctionOpt.isPresent())
    {
      AuctionSession session = auctionOpt.get();
      if (!isRegistered(auctionSession).isRegistered())
      {

        UserAuction userAuction = UserAuction
          .builder()
          .auctionSession(session)
          .build();

        userAuctionRepository.save(userAuction);

        //update number of register
        session.increaseRegisterNumber();
        session = auctionSessionRepository.save(session);

        response.setSuccess(true);
      }
    }
    else
    {
      log.error("Not found auction!");
      response.setError("Auction not found!");
      response.setSuccess(false);
    }
    return response;
  }

  @Override
  public ManipulateAuctionResponse isRegistered(Long id)
  {
    ManipulateAuctionResponse response = new ManipulateAuctionResponse();

    Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(id);
    UserAuction userAuction = null;
    if (auctionOpt.isPresent())
    {
      AuctionSession auctionSession = auctionOpt.get();
      String username = SecurityUtils.getCurrentUsername().orElse(null);
      userAuction = userAuctionRepository.findUserAuctionByCreatedByAndAuctionSession(username,
        auctionSession);

      boolean registered = userAuction != null;

      response.setRegistered(registered);
      response.setStatus(auctionSession.getStatus());
    }

    return response;

  }

  @Override
  @Transactional
  public AuctionDetailResponse handleNext(Long id)
  {
    Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(id);
    AuctionDetailResponse response = new AuctionDetailResponse();
    if (auctionOpt.isPresent())
    {
      AuctionSession auction = auctionOpt.get();
      Integer size = auction.getNumItem();
      Integer nextIdx = auction.getNextLot();

      auction.setCurrLot(nextIdx);
      List<LotInfo> lotInfos = auction.getItemsInSession();

      auction.setNextLot(getNextIndex(lotInfos, nextIdx));

      auction = auctionSessionRepository.save(auction);

      response.setNextLot(auction.getNextLot());
      response.setCurrLot(auction.getCurrLot());
      response.setAuctionDTO(mapping.convertToDto(auction));

      sseService.sendNotificationToAll("next", "Next event");
      sseService.sendNotificationToAll("reload-bids", "Reload bids");
    }
    return response;
  }

  @Override
  public void updateAuction(UpdateItemRequest request)
  {
    ObjectMapper objectMapper = new ObjectMapper();

    Long id = request.getId();
    Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(id);
    if (auctionOpt.isPresent())
    {
      AuctionSession auction = auctionOpt.get();
      if (StringUtils.hasText(request.getStatus()))
      {
        auction.setStatus(request.getStatus());
      }
      try
      {
      auction = auctionSessionRepository.save(auction);

      AuctionMessage message = AuctionMessage.builder()
        .description("Update status")
        .status(request.getStatus())
        .build();

        sseService.sendNotificationToAll("auction-update", objectMapper.writeValueAsString(message));
      }
      catch (JsonProcessingException e)
      {
        log.warn(e.getMessage());
      }
    }
  }

  @Override
  public AuctionDetailResponse handleEnd(Long id)
  {
    return null;
  }

  @Override
  public GetUserAuctionResponse registeredAuctions()
  {
    String username = SecurityUtils.getCurrentUsername().orElse("nctn-admin");
    List<UserAuction> userAuctions = userAuctionRepository.findAllByCreatedBy(username);
    List<UserAuctionDto> auctionDTOList = userAuctions.stream()
      .map(aution -> userAuctionMapping.convertToDto(aution))
      .collect(Collectors.toList());

    return GetUserAuctionResponse.builder()
      .userAuctionDtoList(auctionDTOList).build();
  }

  private Integer getNextIndex(List<LotInfo> lotInfos, Integer currIdx)
  {

    int index = currIdx;
    int size = lotInfos.size();
    while (true)
    {
      if (index + 1 >= size)
      {
        index = 1;
      }
      else
      {
        index = index + 1;
      }
      LotInfo lotInfo = lotInfos.get(index - 1);
      if (!lotInfo.isSold())
      {
        return index;
      }

    }
  }

  @Override
  @Transactional
  public AuctionDetailResponse getAuctionDetail(Long id)
  {
    Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(id);
    if (auctionOpt.isPresent())
    {
      AuctionSession auction = auctionOpt.get();
      AuctionDetailResponse response = new AuctionDetailResponse();
      AuctionDTO auctionDTO = mapping.convertToDto(auction);
      List<LotInfo> lotInfos = auction.getItemsInSession();
      List<LotInfoDto> lotInfoDtoList = lotInfos.stream()
        .map(lotInfo -> lotMapping.convertToDto(lotInfo))
        .collect(Collectors.toList());

      response.setAuctionDTO(auctionDTO);
      response.setLotInfos(lotInfoDtoList);
      response.setCurrLot(auction.getCurrLot());
      response.setNextLot(auction.getNextLot());
      ;
      return response;
    }
    else
    {
      log.warn("Not found auction");
      throw new BadRequestException("Not found auction");
    }

  }

  private void updateItemInSession(AuctionSession session, List<LotInfo> items)
  {
    int order = 1;

    for (LotInfo item : items)
    {
      item.setSession(session);
      item.setOrderInSession(order);

      item = lotInfoRepository.save(item);
      order++;
    }

    session = auctionSessionRepository.save(session);

  }
}
