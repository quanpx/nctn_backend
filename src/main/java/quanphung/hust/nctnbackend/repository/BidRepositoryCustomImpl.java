package quanphung.hust.nctnbackend.repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import quanphung.hust.nctnbackend.domain.BidInfo;
import quanphung.hust.nctnbackend.domain.QBidInfo;

@Repository
public class BidRepositoryCustomImpl implements BidRepositoryCustom
{
  @PersistenceContext
  private EntityManager em;

  @Override
  public BidInfo findBidInfoByUsernameAndLot(String username, Long lotId)
  {
    QBidInfo qBidInfo = QBidInfo.bidInfo;
    JPAQuery<?> query = new JPAQuery<>(em);
    BooleanBuilder whereClause = new BooleanBuilder();
    whereClause.and(qBidInfo.createdBy.eq(username))
      .and(qBidInfo.lotInfo.id.eq(lotId));
    OrderSpecifier<String> orderSpecifier= new OrderSpecifier<>(Order.DESC, qBidInfo.createdDate.stringValue());

    return query
      .select(qBidInfo)
      .from(qBidInfo)
      .where(whereClause)
      .orderBy(orderSpecifier)
      .fetchFirst();
  }
}
