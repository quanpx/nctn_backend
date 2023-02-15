package quanphung.hust.nctnbackend.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LotFilter
{
  private String name;

  private Boolean isSold;

  private Long minPrice;

  private Long maxPrice;

  private Long session;

  private Integer orderInSession;
}
