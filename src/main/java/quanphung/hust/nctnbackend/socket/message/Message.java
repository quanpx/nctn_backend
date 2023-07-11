package quanphung.hust.nctnbackend.socket.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message
{
  @JsonProperty("message")
  private String message;

  @JsonProperty("num_curr_users")
  private Integer users;
}
