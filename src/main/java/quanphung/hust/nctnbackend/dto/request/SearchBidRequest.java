package quanphung.hust.nctnbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchBidRequest {

    private String username;

    private String status;

    private Integer page;

    private Integer size;

    private String[] orderByColumns;

}
