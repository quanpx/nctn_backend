package quanphung.hust.nctnbackend.dto.request;

import java.sql.Timestamp;
import java.util.List;

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
public class CreateAuctionRequest
{
  @JsonProperty("item_ids")
  private List<Long> itemIds;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("start_time")
  private Long startTime;

  @JsonProperty("stream_link")
  private String streamLink;

  @JsonProperty("is_stream")
  private boolean isStream;

  @JsonProperty("image_url")
  private String imageUrl;




}
