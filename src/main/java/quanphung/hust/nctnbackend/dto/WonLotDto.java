package quanphung.hust.nctnbackend.dto;

import java.util.List;

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
public class WonLotDto
{

  @JsonProperty("auction")
  private AuctionDTO auctionDTO;

  @JsonProperty("is_paid")
  private boolean isPaid;

  @JsonProperty("total")
  private Long total;

  @JsonProperty("won_lots")
  private List<LotInfoDto> lotInfoDtos;

}
