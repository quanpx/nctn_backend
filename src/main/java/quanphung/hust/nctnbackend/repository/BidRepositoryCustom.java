package quanphung.hust.nctnbackend.repository;

import quanphung.hust.nctnbackend.domain.BidInfo;

public interface BidRepositoryCustom
{
  BidInfo findBidInfoByUsernameAndLot(String username,Long lotId);
}
