package quanphung.hust.nctnbackend.domain;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "lot_info")
public class LotInfo
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name="description")
  private String description;

  @Column(name="current_price")
  private Long currentPrice;

  @Column(name = "estm_price")
  private Long estmPrice;

  @Column(name = "is_sold")
  private boolean isSold;

  @Column(name = "sold_price")
  private Long soldPrice;

  @Column(name="step")
  private Long step;

  @Column(name="order_in_session")
  private Integer orderInSession;

  @ManyToOne
  @JoinColumn(name = "session_id")
  private AuctionSession session;

}
