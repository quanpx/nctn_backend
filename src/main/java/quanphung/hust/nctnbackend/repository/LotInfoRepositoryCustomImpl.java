package quanphung.hust.nctnbackend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.domain.QLotInfo;
import quanphung.hust.nctnbackend.dto.filter.LotFilter;
import quanphung.hust.nctnbackend.utils.DataFilterUtils;

@Repository
public class LotInfoRepositoryCustomImpl implements LotInfoRepositoryCustom
{
  @PersistenceContext
  private EntityManager em;

  @Override
  public List<LotInfo> findLotInfoByPagingAndFilter(
    Integer size,
    Integer page,
    LotFilter filter,
    OrderSpecifier<String>[] orderByColumns)
  {
    size = DataFilterUtils.resolveSize(size);
    page = DataFilterUtils.resolvePage(page);

    BooleanBuilder whereClause = buildWhereClause(filter);
    JPAQuery<?> query = new JPAQuery<>(em);

    QLotInfo qLotInfo = QLotInfo.lotInfo;

    return query.select(qLotInfo)
      .from(qLotInfo)
      .where(whereClause)
      .limit(size)
      .offset((long)page * size)
      .orderBy(orderByColumns)
      .fetch();
  }

  private BooleanBuilder buildWhereClause(LotFilter filter)
  {
    return new BooleanBuilder();
  }

  @Override
  public Long countLotInfoByPagingAndFilter(
    Integer size,
    Integer page,
    LotFilter filter)
  {
    size = DataFilterUtils.resolveSize(size);
    page = DataFilterUtils.resolvePage(page);

    BooleanBuilder whereClause = buildWhereClause(filter);
    JPAQuery<?> query = new JPAQuery<>(em);

    QLotInfo qLotInfo = QLotInfo.lotInfo;
    return query.select(qLotInfo)
      .from(qLotInfo)
      .where(whereClause)
      .limit(size)
      .offset((long)page * size)
      .fetchCount();
  }
}
