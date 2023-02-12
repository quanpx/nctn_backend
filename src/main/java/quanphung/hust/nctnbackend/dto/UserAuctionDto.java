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
public class UserAuctionDto
{
  @JsonProperty("auction")
  private AuctionDTO auctionDTO;

  @JsonProperty("created_at")
  private Timestamp createdAt;
}