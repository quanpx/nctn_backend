package quanphung.hust.nctnbackend.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quanphung.hust.nctnbackend.dto.AuctionDTO;
import quanphung.hust.nctnbackend.dto.LotInfoDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuctionDetailResponse
{
  @JsonProperty("lots")
  private List<LotInfoDto> lotInfos;

  @JsonProperty("auction")
  private AuctionDTO auctionDTO;
}
