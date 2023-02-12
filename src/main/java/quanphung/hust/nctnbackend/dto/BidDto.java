package quanphung.hust.nctnbackend.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BidDto
{

  @JsonProperty("id")
  private Long Id;

  @JsonProperty("lot_id")
  private Long lotId;

  @JsonProperty("lot_name")
  private String lotName;

  @JsonProperty("lot_description")
  private String lotDescription;

  @JsonProperty("bid_price")
  private Long bidPrice;

  @JsonProperty("bid_time")
  private int bidTime;

  @JsonProperty("created_at")
  private Timestamp createdAt;

  @JsonProperty("status")
  private String status;

  @JsonProperty("image_url")
  private String lotImage;

  @JsonProperty("owner")
  private String owner;

}
