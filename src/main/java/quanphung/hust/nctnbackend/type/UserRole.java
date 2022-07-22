package quanphung.hust.nctnbackend.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole
{

  SYSTEM_ADMIN("nctn-admin"),
  ADMIN("admin"),
  USER("user");

  private String value;
}