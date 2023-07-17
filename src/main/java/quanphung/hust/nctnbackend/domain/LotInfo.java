package quanphung.hust.nctnbackend.domain;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.HashCodeExclude;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lot_info")
public class LotInfo extends InitializationInfo
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Lob
  @Column(name="description")
  private String description;

  @Column(name="current_price")
  private Long currentPrice;

  @Column(name="init_price")
  private Long initPrice;

  @Column(name = "estm_price")
  private String estmPrice;

  @Column(name = "is_sold")
  private boolean isSold;

  @Column(name = "sold_price")
  private Long soldPrice;

  @Column(name="step")
  private Long step;

  @Column(name="bid_num")
  private int bidNum;

  @Column(name="follower")
  private int follower;

  @Column(name="order_in_session")
  private Integer orderInSession;

  @Column(name="sold_for")
  private String soldFor;

  @Column(name = "image_url")
  private String imageUrl;


  @ManyToOne
  @JoinColumn(name = "session_id")
  @HashCodeExclude
  @ToString.Exclude
  private AuctionSession session;

  public void increaseBidNum()
  {
    this.bidNum++;
  }
}
