package quanphung.hust.nctnbackend.mapping;

import org.springframework.stereotype.Component;
import quanphung.hust.nctnbackend.domain.BidInfo;
import quanphung.hust.nctnbackend.dto.BidDto;

@Component
public class BidMapping implements BaseMapping<BidInfo, BidDto> {
    @Override
    public BidDto convertToDto(BidInfo bidInfo) {
        return BidDto.builder()
                .bidPrice(bidInfo.getBidPrice())
                .bidTime(bidInfo.getBidTime())
                .Id(bidInfo.getId())
                .lotId(bidInfo.getLotInfo().getId())
                .lotImage(bidInfo.getLotInfo().getImageUrl())
                .lotName(bidInfo.getLotInfo().getName())
                .lotDescription(bidInfo.getLotInfo().getDescription())
                .status(bidInfo.getStatus())
                .build();
    }

    @Override
    public BidInfo convertToEntity(BidDto bidDto) {
        return null;
    }
}
