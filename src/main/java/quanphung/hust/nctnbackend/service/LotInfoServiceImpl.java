package quanphung.hust.nctnbackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.OrderSpecifier;
import lombok.extern.slf4j.Slf4j;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.filter.LotFilter;
import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;
import quanphung.hust.nctnbackend.dto.request.GetLotRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.dto.response.GetLotResponse;
import quanphung.hust.nctnbackend.exception.InvalidSortColumnException;
import quanphung.hust.nctnbackend.exception.InvalidSortOrderException;
import quanphung.hust.nctnbackend.mapping.LotMapping;
import quanphung.hust.nctnbackend.repository.LotInfoRepository;
import quanphung.hust.nctnbackend.repository.orderutils.LotOrderUtils;

@Service
@Slf4j
public class LotInfoServiceImpl implements LotInfoService
{
  @Autowired
  private LotInfoRepository lotInfoRepository;

  @Autowired
  private LotMapping mapping;

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
    if (lotOpt.isEmpty())
    {
      log.error("Id not available!");
      throw new BadRequestException(("Id not available!"));
    }
    LotInfo lot = lotOpt.get();
    return LotInfoDto.builder()
      .name(lot.getName())
      .currentPrice(lot.getCurrentPrice())
      .description(lot.getDescription())
      .estmPrice(lot.getEstmPrice())
      .orderLot(lot.getOrderInSession())
      .session(lot.getSession().getName())
      .startTime(lot.getSession().getStartTime())
      .imageUrl(lot.getImageUrl())
      .build();
  }

  @Override
  public GetLotResponse getLotInfos(GetLotRequest request)
  {
    Integer page = request.getPage();
    Integer size = request.getSize();
    LotFilter filter = LotFilter.builder()
      .name(request.getName())
      .isSold(request.getIsSold())
      .orderInSession(request.getOrderInSession())
      .maxEstmPrice(request.getMaxEstmPrice())
      .minEstmPrice(request.getMinEstmPrice())
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
}
