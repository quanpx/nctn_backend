package quanphung.hust.nctnbackend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.domain.QAuctionSession;
import quanphung.hust.nctnbackend.dto.filter.AuctionFilter;
import quanphung.hust.nctnbackend.utils.DataFilterUtils;

@Repository
public class AuctionRepositoryCustomImpl implements AuctionRepositoryCustom
{
  @PersistenceContext
  private EntityManager em;

  @Override
  public List<AuctionSession> findAuctionByPagingAndFilter(Integer size, Integer page, AuctionFilter filter,
    OrderSpecifier<String>[] orderByColumns)
  {
    size = DataFilterUtils.resolveSize(size);
    page = DataFilterUtils.resolvePage(page);

    BooleanBuilder whereClause = buildWhereClause(filter);
    JPAQuery<?> query = new JPAQuery<>(em);

    QAuctionSession auctionSession = QAuctionSession.auctionSession;

    return query.select(auctionSession)
      .from(auctionSession)
      .where(whereClause)
      .limit(size)
      .offset((long)page * size)
      .orderBy(orderByColumns)
      .fetch();
  }

  private BooleanBuilder buildWhereClause(AuctionFilter filter)
  {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    return booleanBuilder;
  }

  @Override
  public Long countAuctionByPagingAndFilter(
    Integer size,
    Integer page,
    AuctionFilter filter)
  {
    size = DataFilterUtils.resolveSize(size);
    page = DataFilterUtils.resolvePage(page);

    BooleanBuilder whereClause = buildWhereClause(filter);
    JPAQuery<?> query = new JPAQuery<>(em);

    QAuctionSession auctionSession = QAuctionSession.auctionSession;

    return query.select(auctionSession)
      .from(auctionSession)
      .where(whereClause)
      .limit(size)
      .offset((long)page * size)
      .fetchCount();
  }
}
