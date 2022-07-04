package quanphung.hust.nctnbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.AuctionSession;

@Repository
public interface AuctionSessionRepository extends JpaRepository<AuctionSession, Long>
{
}