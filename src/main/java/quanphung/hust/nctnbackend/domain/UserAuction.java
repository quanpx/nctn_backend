package quanphung.hust.nctnbackend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "user_auction")
public class UserAuction extends Auditable
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(name = "status_in_auction")
  private String statusUser;

  @Column(name="is_registered")
  private boolean isRegistered;


  @Column(name = "request_status")
  private String requestStatus;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seesion_id")
  private AuctionSession auctionSession;


}


