package quanphung.hust.nctnbackend.socket.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidMessage extends Message
{
  private String from;

  private String to;

  private Double price;
}
