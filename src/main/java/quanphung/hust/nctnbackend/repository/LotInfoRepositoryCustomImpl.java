package quanphung.hust.nctnbackend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
  @Transactional
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
      .offset((long)(page-1) * size)
      .orderBy(orderByColumns)
      .fetch();
  }

  private BooleanBuilder buildWhereClause(LotFilter filter)
  {
    QLotInfo qLotInfo = QLotInfo.lotInfo;
    BooleanBuilder builder = new BooleanBuilder();

    String name = filter.getName();
    if (StringUtils.hasText(name))
    {
      builder.and(qLotInfo.name.containsIgnoreCase(name));
    }

    String soldFor = filter.getSoldFor();
    if(StringUtils.hasText(soldFor))
    {
      builder.and(qLotInfo.soldFor.eq(soldFor));
    }

    Long session = filter.getSession();
    if(session != null)
    {
      builder.and(qLotInfo.session.Id.eq(session));
    }

    Boolean isSold = filter.getIsSold();
    if (isSold != null)
    {
      builder.and(qLotInfo.isSold.eq(isSold));
    }

    Long minPrice = filter.getMinPrice();
    if (minPrice != null)
    {
      builder.and(qLotInfo.initPrice.goe(minPrice));
    }

    Long maxPrice = filter.getMaxPrice();
    if (maxPrice != null)
    {
      builder.and(qLotInfo.initPrice.loe(maxPrice));
    }

    return builder;
  }

  @Override
  public Long countLotInfoByPagingAndFilter(
    Integer size,
    Integer page,
    LotFilter filter)
  {
    BooleanBuilder whereClause = buildWhereClause(filter);
    JPAQuery<?> query = new JPAQuery<>(em);

    QLotInfo qLotInfo = QLotInfo.lotInfo;
    return query.select(qLotInfo)
      .from(qLotInfo)
      .where(whereClause)
      .fetchCount();
  }

  @Override
  public List<String> getWonUsers(Long id)
  {
    JPAQuery<?> query = new JPAQuery<>(em);
    QLotInfo qLotInfo = QLotInfo.lotInfo;

    return query.select(qLotInfo.soldFor)
      .from(qLotInfo)
      .where(qLotInfo.session.Id.eq(id))
      .distinct()
      .fetch();
  }
}
