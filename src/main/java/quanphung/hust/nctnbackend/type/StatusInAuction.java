package quanphung.hust.nctnbackend.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusInAuction
{
  JOINED("JOINED"),
  EXIT("EXIT");

  private String status;
}
