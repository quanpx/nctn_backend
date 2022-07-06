package quanphung.hust.nctnbackend.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SessionStatus
{
  CREATED("Created"),
  STARTING("Starting"),
  FINISHED("Finished");

  private  String status;

}
