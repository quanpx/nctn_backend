package quanphung.hust.nctnbackend.repository;

import java.util.List;

import com.querydsl.core.types.OrderSpecifier;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.dto.filter.LotFilter;

public interface LotInfoRepositoryCustom
{
  List<LotInfo> findLotInfoByPagingAndFilter(
    Integer size,
    Integer page,
    LotFilter filter,
    OrderSpecifier<String>[] orderByColumns
  );

  Long countLotInfoByPagingAndFilter(
    Integer size,
    Integer page,
    LotFilter filter
  );
}
