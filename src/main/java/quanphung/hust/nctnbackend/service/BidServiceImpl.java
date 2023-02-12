package quanphung.hust.nctnbackend.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quanphung.hust.nctnbackend.domain.BidInfo;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.dto.BidDto;
import quanphung.hust.nctnbackend.dto.EventDto;
import quanphung.hust.nctnbackend.dto.filter.BidFilter;
import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.SearchBidRequest;
import quanphung.hust.nctnbackend.dto.response.BidResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateBidResponse;
import quanphung.hust.nctnbackend.mapping.BidMapping;
import quanphung.hust.nctnbackend.repository.BidInfoRepository;
import quanphung.hust.nctnbackend.repository.LotInfoRepository;
import quanphung.hust.nctnbackend.type.BidStatus;
import quanphung.hust.nctnbackend.utils.SecurityUtils;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BidServiceImpl implements BidService
{

  @Autowired
  private BidMapping bidMapping;

  @Autowired
  private SseService sseService;

  @Autowired
  private MessageService messageService;

  @Autowired
  private AuctionService auctionService;

  @Autowired
  private LotInfoRepository lotInfoRepository;

  @Autowired
  private BidInfoRepository bidInfoRepository;

  @Override
  @Transactional
  public BidResponse bidLot(BidRequest request)
  {
    BidResponse response = new BidResponse();

    Optional<LotInfo> lotInfoOpt = lotInfoRepository.findById(request.getLotId());

    BidInfo bidInfo;
    if (lotInfoOpt.isPresent())
    {
      LotInfo lotInfo = lotInfoOpt.get();
      Long bidPrice = request.getBidPrice();
      bidInfo = BidInfo.builder()
        .bidPrice(bidPrice)
        .lotInfo(lotInfo)
        .status(BidStatus.BIDDING.getValue())
        .build();

      bidInfo = bidInfoRepository.save(bidInfo);

      Long currentPrice = lotInfo.getCurrentPrice() < bidPrice ? bidPrice : lotInfo.getCurrentPrice();

      lotInfo.increaseBidNum();
      lotInfo.setCurrentPrice(currentPrice);
      lotInfo = lotInfoRepository.save(lotInfo);

      sseService.sendNotificationToAll("bid","New bid");
      sseService.sendNotificationToAll("auction-update","New current price");

    }
    else {
      response.setError("Lot not found!");
      return response;
    }

    boolean isRegistered = auctionService.isRegistered(request.getAuctionId()).isRegistered();
    if(!isRegistered)
    {
      auctionService.registerAuction(request.getAuctionId());
    }


    Object[] params = new Object[] { request.getBidPrice() };
    String message = messageService.resolveMessage("bid", params, null);

    log.info(message);
    response.setMessage(message);
    return response;
}

  @Override
  @Transactional
  public BidResponse getAllBid(SearchBidRequest request)
  {
    BidFilter filter = BidFilter.builder()
      .username(request.getUsername())
      .status(request.getStatus())
      .build();
    Integer page = request.getPage();
    Integer size = request.getSize();
    List<BidInfo> bidInfoList = bidInfoRepository.findBidInfoByFilterAndPaging(filter, null, page, size);
    List<BidDto> bidDtos = bidInfoList.stream()
      .map(bidInfo -> bidMapping.convertToDto(bidInfo))
      .collect(Collectors.toList());

    long count = bidInfoRepository.countBidInfoByFilterAndPaging(filter, page, size);

    return BidResponse.builder()
      .bidDtos(bidDtos)
      .count(count)
      .build();
  }

  @Override
  public ManipulateBidResponse isBid(Long lotId)
  {
    ManipulateBidResponse response = new ManipulateBidResponse();
    List<BidInfo> bidInfoList = findBidedInfo(lotId);
    if (bidInfoList != null && !bidInfoList.isEmpty())
    {
      response.setBid(true);
      response.setPrice(bidInfoList.get(0).getBidPrice());
    }
   else {
     response.setBid(false);
    }

    return response;
  }

  @Override
  public BidResponse getBidInAuction(Long lotId)
  {
    Optional<LotInfo> lotInfoOpt = lotInfoRepository.findById(lotId);

    if (lotInfoOpt.isPresent())
    {
      LotInfo lotInfo = lotInfoOpt.get();

      List<BidInfo> bidInfoList = bidInfoRepository.findAllByLotInfo(lotInfo);
      List<BidDto> bidDtos = bidInfoList.stream()
        .map(bidInfo -> bidMapping.convertToDto(bidInfo))
        .collect(Collectors.toList());

      return BidResponse.builder().bidDtos(bidDtos).build();
    }
    return new BidResponse();

  }

  private List<BidInfo> findBidedInfo(Long lotId)
  {
    Optional<String> usernameOpt = SecurityUtils.getCurrentUsername();
    if (usernameOpt.isPresent())
    {
      String username = usernameOpt.get();
      return bidInfoRepository.findBidInfoByUsernameAndLot(username, lotId);

    }
    return null;

  }
}
