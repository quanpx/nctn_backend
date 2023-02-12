package quanphung.hust.nctnbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quanphung.hust.nctnbackend.dto.BidDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BidResponse
{
  @JsonProperty("bids")
  private List<BidDto> bidDtos;

  @JsonProperty("count")
  private Long count;

  @JsonProperty("error")
  private String error;

  @JsonProperty("message")
  private String message;


}
