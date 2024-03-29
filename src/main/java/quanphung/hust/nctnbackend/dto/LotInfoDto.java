package quanphung.hust.nctnbackend.dto;

import java.sql.Timestamp;

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
public class LotInfoDto
{

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("current_price")
  private Long currentPrice;

  @JsonProperty("init_price")
  private Long initPrice;

  @JsonProperty("estm_price")
  private String estmPrice;

  @JsonProperty("order_in_lot")
  private Integer orderLot;

  @JsonProperty("session")
  private String session;

  @JsonProperty("is_sold")
  private boolean isSold;

  @JsonProperty("sold_for")
  private String soldFor;

  @JsonProperty("sold_price")
  private Long soldPrice;

  @JsonProperty("start_time")
  private Timestamp startTime;

  @JsonProperty("bid_num")
  private Integer bidNum;

  @JsonProperty("image_url")
  private String imageUrl;
}
