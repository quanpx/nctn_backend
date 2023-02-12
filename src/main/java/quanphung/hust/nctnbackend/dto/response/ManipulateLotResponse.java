package quanphung.hust.nctnbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quanphung.hust.nctnbackend.dto.LotInfoDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManipulateLotResponse
{
  @JsonProperty("lot_info")
  private LotInfoDto lotInfoDto;

  @JsonProperty("message")
  private String message;

  @JsonProperty("error")
  private String error;
}
