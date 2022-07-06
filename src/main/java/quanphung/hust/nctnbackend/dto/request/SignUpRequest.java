package quanphung.hust.nctnbackend.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest
{
  @NonNull
  @JsonProperty("email")
  private String email;

  @NonNull
  @JsonProperty("username")
  private String username;

  @NonNull
  @JsonProperty("password")
  private String password;

  @JsonProperty("enabled")
  private boolean enabled;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("phoen")
  private String phone;
}
