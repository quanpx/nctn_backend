package quanphung.hust.nctnbackend.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quanphung.hust.nctnbackend.dto.AuctionDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAuctionResponse
{
  @JsonProperty("auctions")
  private List<AuctionDTO> auctionDTOList;

  @JsonProperty("count")
  private Long count;
}

