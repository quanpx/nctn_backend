package quanphung.hust.nctnbackend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "won_lot")
public class WonLot extends InitializationInfo
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "owner")
  private String owner;

  @Column(name="sold_price")
  private Long soldPrice;

  @Column(name="paid")
  private boolean paid;

  @ManyToOne
  @JoinColumn(name = "session_id",referencedColumnName = "id")
  @HashCodeExclude
  @ToString.Exclude
  private AuctionSession session;

  @OneToOne
  @JoinColumn(name = "lot_id", referencedColumnName = "id")
  @HashCodeExclude
  @ToString.Exclude
  private LotInfo lot;


}
