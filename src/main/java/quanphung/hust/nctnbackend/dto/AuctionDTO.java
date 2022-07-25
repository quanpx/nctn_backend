package quanphung.hust.nctnbackend.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionDTO
{
  @JsonProperty("id")
  private String Id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("num_item")
  private Integer numItem;

  @JsonProperty("start_time")
  private Timestamp startTime;

  @JsonProperty("register_num")
  private Integer registerNum;

  @JsonProperty("image_url")
  private String imageUrl;
}
