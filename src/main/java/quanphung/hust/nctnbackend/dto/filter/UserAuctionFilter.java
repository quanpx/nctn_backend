package quanphung.hust.nctnbackend.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuctionFilter
{
  private Long auctionId;

  private String owner;

  private String status;
}
