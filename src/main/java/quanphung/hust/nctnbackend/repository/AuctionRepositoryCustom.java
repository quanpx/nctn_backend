package quanphung.hust.nctnbackend.repository;

import java.util.List;

import com.querydsl.core.types.OrderSpecifier;
import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.dto.filter.AuctionFilter;

public interface AuctionRepositoryCustom
{
  List<AuctionSession> findAuctionByPagingAndFilter(
    Integer size,
    Integer page,
    AuctionFilter filter,
    OrderSpecifier<String>[] orderByColumns
    );

  Long countAuctionByPagingAndFilter(
    Integer size,
    Integer page,
    AuctionFilter filter
  );
}
