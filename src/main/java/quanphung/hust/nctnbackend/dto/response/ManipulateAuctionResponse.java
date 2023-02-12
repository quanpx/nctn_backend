package quanphung.hust.nctnbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManipulateAuctionResponse
{
  @JsonProperty("is_success")
  private boolean isSuccess;

  @JsonProperty("message")
  private String message;

  @JsonProperty("is_registered")
  private boolean isRegistered;

  @JsonProperty("status")
  private String status;

  @JsonProperty("error")
  private String error;
}
