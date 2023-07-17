package quanphung.hust.nctnbackend.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quanphung.hust.nctnbackend.domain.LotInfo;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDto
{
  @JsonProperty("id")
  private Long id;

  @JsonProperty("is_paid")
  private boolean isPaid;

  @JsonProperty("lots")
  private Set<LotInfoDto> lots;

  @JsonProperty("customer")
  private String customer;

  @JsonProperty("total")
  private Long total;

  @JsonProperty("auction")
  private AuctionDTO auctionDTO;

  @JsonProperty("time")
  private String time;
}
