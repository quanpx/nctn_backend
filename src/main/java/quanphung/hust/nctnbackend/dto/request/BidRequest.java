package quanphung.hust.nctnbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BidRequest
{
  @JsonProperty("bid_id")
  private Long bidId;

  @JsonProperty("user")
  private String user;

  @JsonProperty("lot_id")
  private Long lotId;

  @JsonProperty("auction_id")
  private Long auctionId;

  @JsonProperty("bid_price")
  private Long bidPrice;
}
