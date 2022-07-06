package quanphung.hust.nctnbackend.mapping;

import org.springframework.stereotype.Component;

import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.dto.AuctionDTO;

@Component
public class AuctionMapping implements BaseMapping<AuctionSession, AuctionDTO>
{
  @Override
  public  AuctionDTO convertToDto(AuctionSession session)
  {
    return AuctionDTO.builder()
      .Id(session.getId().toString())
      .name(session.getName())
      .description(session.getDescription())
      .numItem(session.getNumItem())
      .startTime(session.getStartTime())
      .registerNum(session.getRegisterNum())
      .build();
  }

  @Override
  public AuctionSession convertToEntity(AuctionDTO auctionDTO)
  {
    return null;
  }
}
