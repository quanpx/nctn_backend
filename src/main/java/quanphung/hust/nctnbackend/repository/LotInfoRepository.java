package quanphung.hust.nctnbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.LotInfo;

@Repository
public interface LotInfoRepository extends JpaRepository<LotInfo, Long>
{
}