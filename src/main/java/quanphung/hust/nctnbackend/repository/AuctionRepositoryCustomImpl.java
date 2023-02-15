package quanphung.hust.nctnbackend.repository;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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

    QAuctionSession auctionSession = QAuctionSession.auctionSession;
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    String text = filter.getText();

    if (StringUtils.hasText(text))
    {
      booleanBuilder.and(auctionSession.name.containsIgnoreCase(text));
    }

    String status = filter.getStatus();
    if (StringUtils.hasText(status))
    {
      booleanBuilder.and(auctionSession.status.equalsIgnoreCase(status));
    }

    Long value = filter.getStartTime();
    if (value != null)
    {
      Timestamp startTime = new Timestamp(value);
      booleanBuilder.and(auctionSession.startTime.after(startTime));
    }

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
