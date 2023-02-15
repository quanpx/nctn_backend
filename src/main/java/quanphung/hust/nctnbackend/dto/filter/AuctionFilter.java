package quanphung.hust.nctnbackend.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionFilter
{
  private Long createdAt;

  private Long startTime;

  private String status;

  private String text;
}
