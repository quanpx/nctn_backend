package quanphung.hust.nctnbackend.dto.response;

import java.util.List;

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
public class GetLotResponse
{
  @JsonProperty("lots")
  private List<LotInfoDto> lotInfoDtoList;

  @JsonProperty("count")
  private Long count;
}
