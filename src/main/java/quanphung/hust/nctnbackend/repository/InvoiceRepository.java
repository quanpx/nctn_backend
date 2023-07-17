package quanphung.hust.nctnbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.domain.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>
{
  @Query("select i from Invoice i where i.customer = ?1 and i.session = ?2")
  Optional<Invoice> findInvoiceByCustomerAndSession(String customer, AuctionSession session);
}