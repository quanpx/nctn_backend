package quanphung.hust.nctnbackend.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auction_session")
public class AuctionSession extends InitializationInfo
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name="num_item")
  private Integer numItem;

  @Column(name = "start_time")
  private Timestamp startTime;

  @Column(name = "steam_link")
  private String streamLink;

  @Column(name = "is_stream")
  private boolean isStream;

  @Column(name="pending_request")
  private int pending;

  @Column(name = "status")
  private String status;

  @Column(name = "registerNum")
  private int registerNum;

  @Column(name="image_url")
  private String imageUrl;

  @Column(name = "current_lot")
  private Integer currLot;

  @Column(name = "next_lot")
  private Integer nextLot;

  @OneToMany(fetch = FetchType.LAZY,mappedBy = "session")
  @OrderBy("orderInSession ASC")
  private List<LotInfo> itemsInSession;

  public void increaseRegisterNumber()
  {
    this.registerNum++;
  }

  public void increasePendingRequest()
  {
    this.pending++;
  }

  public void decreasePendingRequest() {this.pending--;}

}
