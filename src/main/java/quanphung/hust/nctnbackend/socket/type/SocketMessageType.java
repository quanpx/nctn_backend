package quanphung.hust.nctnbackend.socket.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocketMessageType
{
  NEW_BID("new-bid");
  private String type;

}
