package quanphung.hust.nctnbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAuctionRequest
{
  private Integer page;

  private Integer size;

  private Long createdAt;

  private Long startTime;

  private String status;

  private String[] orderByColumns;
}
