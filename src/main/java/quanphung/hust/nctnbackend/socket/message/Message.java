package quanphung.hust.nctnbackend.socket.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.response.BidResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message
{
  @JsonProperty("message")
  private String message;

  @JsonProperty("num_curr_users")
  private Integer users;

  @JsonProperty("type")
  private String type;

  @JsonProperty("bid_info")
  private BidResponse bidInfo;

  @JsonProperty("lot")
  private LotInfoDto lotInfoDto;

  @JsonProperty("curr_lot")
  private LotInfoDto currLot;

  @JsonProperty("next_lot")
  private Integer nextLot;

}
