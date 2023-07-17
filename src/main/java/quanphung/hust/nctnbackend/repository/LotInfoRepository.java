package quanphung.hust.nctnbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.domain.LotInfo;

@Repository
public interface LotInfoRepository extends JpaRepository<LotInfo, Long>,LotInfoRepositoryCustom
{
  @Query("select l from LotInfo l where l.orderInSession = ?1 and l.session = ?2")
  Optional<LotInfo> findByOrderInSessionAndSession(Integer order, AuctionSession session);
}