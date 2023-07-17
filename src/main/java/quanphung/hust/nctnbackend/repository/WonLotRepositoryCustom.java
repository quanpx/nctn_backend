package quanphung.hust.nctnbackend.repository;

import java.util.List;

import quanphung.hust.nctnbackend.domain.WonLot;

public interface WonLotRepositoryCustom
{
  List<WonLot> findWonLots(String username, Long id);
}
