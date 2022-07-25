package quanphung.hust.nctnbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quanphung.hust.nctnbackend.domain.BidInfo;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.dto.BidDto;
import quanphung.hust.nctnbackend.dto.filter.BidFilter;
import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.SearchBidRequest;
import quanphung.hust.nctnbackend.dto.response.BidResponse;
import quanphung.hust.nctnbackend.mapping.BidMapping;
import quanphung.hust.nctnbackend.repository.BidInfoRepository;
import quanphung.hust.nctnbackend.repository.LotInfoRepository;
import quanphung.hust.nctnbackend.type.BidStatus;
import quanphung.hust.nctnbackend.utils.SecurityUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BidServiceImpl implements BidService{

    @Autowired
    private BidMapping bidMapping;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private LotInfoRepository lotInfoRepository;

    @Autowired
    private BidInfoRepository bidInfoRepository;

    @Override
    public BidResponse bidLot(BidRequest request) {
        BidResponse response = new BidResponse();

        Optional<LotInfo> lotInfoOpt = lotInfoRepository.findById(request.getLotId());

        //Search in db , whether exist bid that bidded before
        BidInfo bidInfo;
        if (lotInfoOpt.isPresent()) {
            LotInfo lotInfo = lotInfoOpt.get();
            bidInfo = findBidedInfo(request.getLotId());

            if (bidInfo != null) {
                log.info(bidInfo.getCreatedDate().toString());
                bidInfo = BidInfo.builder()
                        .bidPrice(request.getBidPrice())
                        .lotInfo(lotInfo)
                        .bidTime(bidInfo.getBidTime() + 1)
                        .status(BidStatus.BIDDING.getValue())
                        .build();
                bidInfoRepository.save(bidInfo);
            } else {
                bidInfo = BidInfo.builder()
                        .bidPrice(request.getBidPrice())
                        .lotInfo(lotInfo)
                        .status(BidStatus.BIDDING.getValue())
                        .build();
                bidInfoRepository.save(bidInfo);
            }
            auctionService.registerAuction(request.getAuctionId());

        } else {
            response.setError("Lot item not found!");
        }

        return response;
    }

    @Override
    public BidResponse getAllBid(SearchBidRequest request) {
        BidFilter filter = BidFilter.builder()
                .username(request.getUsername())
                .status(request.getStatus())
                .build();
        Integer page = request.getPage();
        Integer size = request.getSize();
        List<BidInfo> bidInfoList = bidInfoRepository.findBidInfoByFilterAndPaging(filter,null,page,size);
        List<BidDto> bidDtos = bidInfoList.stream()
                .map(bidInfo -> bidMapping.convertToDto(bidInfo))
                .collect(Collectors.toList());

        long count = bidInfoRepository.countBidInfoByFilterAndPaging(filter,page,size);

    return BidResponse.builder()
            .bidDtos(bidDtos)
            .count(count)
            .build();
    }

    private BidInfo findBidedInfo(Long lotId)
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
