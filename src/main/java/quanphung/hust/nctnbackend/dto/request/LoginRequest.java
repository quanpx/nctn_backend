package quanphung.hust.nctnbackend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest
{
  @NotNull
  @JsonProperty("username")
  private String userName;

  @NotNull
  @JsonProperty("password")
  private String password;

}
