package quanphung.hust.nctnbackend.dto.sse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BidMessage {

    @JsonProperty("price")
    private Long price;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("name")
    private String name;

}
