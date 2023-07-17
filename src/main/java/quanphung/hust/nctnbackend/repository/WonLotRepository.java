package quanphung.hust.nctnbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.WonLot;

@Repository
public interface WonLotRepository extends JpaRepository<WonLot, Long>,WonLotRepositoryCustom
{
}