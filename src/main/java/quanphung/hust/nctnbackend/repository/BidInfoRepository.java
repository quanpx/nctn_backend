package quanphung.hust.nctnbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.BidInfo;

@Repository
public interface BidInfoRepository extends JpaRepository<BidInfo, Long>,BidRepositoryCustom
{
}