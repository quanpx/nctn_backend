package quanphung.hust.nctnbackend.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "invoice")
public class Invoice extends Auditable
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "is_paid")
  private boolean isPaid;

  @Column(name = "customer")
  private String customer;

  @Column(name = "total")
  private Long total;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "invoice_lots", joinColumns = @JoinColumn(name = "id"))
  @Column(name = "lots_ids")
  private Set<Long> lotIds;

  public void addLotId(long jobId)
  {
    if (lotIds == null)
    {
      lotIds = new HashSet<>();
    }
    lotIds.add(jobId);
  }
  @ManyToOne
  @JoinColumn(name = "session_id",referencedColumnName = "id")
  @HashCodeExclude
  @ToString.Exclude
  private AuctionSession session;


}
