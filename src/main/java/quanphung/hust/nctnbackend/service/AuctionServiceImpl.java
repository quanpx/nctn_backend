package quanphung.hust.nctnbackend.service;

import com.querydsl.core.types.OrderSpecifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.domain.BidInfo;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.domain.UserAuction;
import quanphung.hust.nctnbackend.dto.AuctionDTO;
import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.filter.AuctionFilter;
import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.GetAuctionRequest;
import quanphung.hust.nctnbackend.dto.response.AuctionDetailResponse;
import quanphung.hust.nctnbackend.dto.response.GetAuctionResponse;
import quanphung.hust.nctnbackend.exception.InvalidSortColumnException;
import quanphung.hust.nctnbackend.exception.InvalidSortOrderException;
import quanphung.hust.nctnbackend.mapping.AuctionMapping;
import quanphung.hust.nctnbackend.mapping.LotMapping;
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
public class AuctionServiceImpl implements AuctionService {
    private static final String SYSTEM_NAME = "nctn_system";
    @Autowired
    private AuctionMapping mapping;

    @Autowired
    private LotMapping lotMapping;

    @Autowired
    private LotInfoRepository lotInfoRepository;

    @Autowired
    private AuctionSessionRepository auctionSessionRepository;

    @Autowired
    private UserAuctionRepository userAuctionRepository;

    @Autowired
    private BidInfoRepository bidInfoRepository;

    @Override
    @Transactional
    public void createAuction(CreateAuctionRequest request) {
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
                .build();

        auctionSessionRepository.save(session);

        updateItemInSession(session, items);

    }

    @Override
    public GetAuctionResponse getAuctions(GetAuctionRequest request) {
        Integer page = request.getPage();
        Integer size = request.getSize();
        GetAuctionResponse response;
        AuctionFilter filter = AuctionFilter.builder()
                .createdAt(request.getCreatedAt())
                .startTime(request.getStartTime())
                .status(request.getStatus())
                .build();
        try {
            OrderSpecifier<String>[] orderSpecifiers = AuctionOrderUtils.createOrderBy(request.getOrderByColumns());
            List<AuctionSession> auctionSessions = auctionSessionRepository.findAuctionByPagingAndFilter(page, size, filter,
                    orderSpecifiers);
            List<AuctionDTO> auctionDTOList = auctionSessions.stream()
                    .map(item -> mapping.convertToDto(item))
                    .collect(Collectors.toList());
            Long count = auctionSessionRepository.countAuctionByPagingAndFilter(page, size, filter);

            response = GetAuctionResponse.builder()
                    .count(count)
                    .auctionDTOList(auctionDTOList)
                    .build();
        } catch (InvalidSortColumnException | InvalidSortOrderException e) {
            log.error(e.getMessage());
            throw new BadRequestException("Invalid sort column!");
        }

        return response;
    }

    @Override
    @Transactional
    public void registerAuction(Long auctionSession) {
        Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(auctionSession);
        if (auctionOpt.isPresent()) {
            AuctionSession session = auctionOpt.get();
            if (!verifyExistedRegisterInDb(session)) {


                UserAuction userAuction = UserAuction
                        .builder()
                        .auctionSession(session)
                        .build();

                userAuctionRepository.save(userAuction);

                //update number of register
                session.increaseRegisterNumber();
                auctionSessionRepository.save(session);
            }
        } else {
            log.error("Not found auction!");
            throw new BadRequestException("Not found auction!");
        }

    }

    private boolean verifyExistedRegisterInDb(AuctionSession auctionSession) {
        String username = SecurityUtils.getCurrentUsername().orElse(null);
        UserAuction userAuction = userAuctionRepository.findUserAuctionByCreatedByAndAuctionSession(username, auctionSession);
        return userAuction != null;
    }

    @Override
    public AuctionDetailResponse getAuctionDetail(Long id) {
        Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(id);
        if (auctionOpt.isPresent()) {
            AuctionSession auction = auctionOpt.get();
            AuctionDetailResponse response = new AuctionDetailResponse();
            AuctionDTO auctionDTO = mapping.convertToDto(auction);
            List<LotInfo> lotInfos = auction.getItemsInSession();
            List<LotInfoDto> lotInfoDtoList = lotInfos.stream()
                    .map(lotInfo -> lotMapping.convertToDto(lotInfo))
                    .collect(Collectors.toList());
            response.setAuctionDTO(auctionDTO);
            response.setLotInfos(lotInfoDtoList);
            return response;
        } else {
            log.warn("Not found auction");
            throw new BadRequestException("Not found auction");
        }

    }

    private BidInfo findBidedInfo(Long lotId) {
        Optional<String> usernameOpt = SecurityUtils.getCurrentUsername();
        if (usernameOpt.isPresent()) {
            String username = usernameOpt.get();
            return bidInfoRepository.findBidInfoByUsernameAndLot(username, lotId);
        }
        return null;
    }

    private void updateItemInSession(AuctionSession session, List<LotInfo> items) {
        int order = 1;
        int numSession = session.getItemsInSession().size();
        session.setNumItem(numSession);
        auctionSessionRepository.save(session);

        for (LotInfo item : items) {
            item.setSession(session);
            item.setOrderInSession(order);

            lotInfoRepository.save(item);
            order++;
        }
    }
}
