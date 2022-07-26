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

    @JsonProperty("message")
    private String message;

    @JsonProperty("current_price")
    private String currentPrice;
}
