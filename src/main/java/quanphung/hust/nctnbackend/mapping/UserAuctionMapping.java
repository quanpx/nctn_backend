package quanphung.hust.nctnbackend.mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import quanphung.hust.nctnbackend.domain.UserAuction;
import quanphung.hust.nctnbackend.dto.AuctionDTO;
import quanphung.hust.nctnbackend.dto.UserAuctionDto;

@Component
public class UserAuctionMapping implements BaseMapping<UserAuction, UserAuctionDto>
{
  @Autowired
  private AuctionMapping auctionMapping;

  @Override
  public UserAuctionDto convertToDto(UserAuction userAuction)
  {
    AuctionDTO auctionDTO = auctionMapping.convertToDto(userAuction.getAuctionSession());
    return UserAuctionDto.builder()
      .id(userAuction.getId())
      .status(userAuction.getRequestStatus())
      .owner(userAuction.getCreatedBy())
      .auctionDTO(auctionDTO)
      .createdAt(userAuction.getCreatedDate())
      .build();
  }

  @Override
  public UserAuction convertToEntity(UserAuctionDto userAuctionDto)
  {
    return null;
  }
}
