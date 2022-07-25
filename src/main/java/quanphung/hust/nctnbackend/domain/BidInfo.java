package quanphung.hust.nctnbackend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bid_info")
public class BidInfo extends InitializationInfo
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @Column(name = "bid_price")
  private Long bidPrice;

  @Column(name = "bid_time")
  private int bidTime;

  @Column(name = "bid_status")
  private String status;

  @ManyToOne
  @JoinColumn(name = "lot_id")
  private LotInfo lotInfo;

  public void increaseBidTime()
  {
    this.bidTime++;
  }

}
