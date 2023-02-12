package quanphung.hust.nctnbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.BidInfo;
import quanphung.hust.nctnbackend.domain.LotInfo;

@Repository
public interface BidInfoRepository extends JpaRepository<BidInfo, Long>,BidRepositoryCustom
{
  List<BidInfo> findBidInfoByLotInfoAndCreatedBy(LotInfo lotInfo, String createdBy);

}