package quanphung.hust.nctnbackend.repository;

import com.querydsl.core.types.OrderSpecifier;
import quanphung.hust.nctnbackend.domain.BidInfo;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.dto.filter.BidFilter;

import java.util.List;

public interface BidRepositoryCustom
{
  List<BidInfo> findBidInfoByUsernameAndLot(String username,Long lotId);
  List<BidInfo> findBidInfoByFilterAndPaging(BidFilter filter, OrderSpecifier<String>[] orderSpecifiers,Integer page,Integer size);
  long countBidInfoByFilterAndPaging(BidFilter filter,Integer page,Integer size);

  List<BidInfo> findAllByLotInfo(LotInfo lotInfo);
}
