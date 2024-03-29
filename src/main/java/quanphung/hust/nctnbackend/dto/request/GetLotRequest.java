package quanphung.hust.nctnbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetLotRequest
{
  private Integer page;

  private Integer size;

  private String name;

  private Boolean isSold;

  private Long minPrice;

  private Long maxPrice;

  private Long session;

  private Integer orderInSession;

  private String[] orderByColumns;

}
