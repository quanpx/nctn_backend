package quanphung.hust.nctnbackend.dto.email;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quanphung.hust.nctnbackend.dto.LotInfoDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails
{
  // Class data members
  private String recipient;
  private String receiver;
  private String msgBody;
  private Long auctionId;
  private String auctionName;
  private String time;
  private String subject;
  private String attachment;
  private String lotName;
  private Long total;
  private List<LotInfoDto> wonLots;
}