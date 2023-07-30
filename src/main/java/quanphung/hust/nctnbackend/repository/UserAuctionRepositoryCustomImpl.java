package quanphung.hust.nctnbackend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import quanphung.hust.nctnbackend.domain.QUserAuction;
import quanphung.hust.nctnbackend.domain.UserAuction;
import quanphung.hust.nctnbackend.dto.filter.UserAuctionFilter;


public class UserAuctionRepositoryCustomImpl implements UserAuctionRepositoryCustom
{

  @PersistenceContext
  private EntityManager em;

  public List<UserAuction> findAllByFilterAndOrders(UserAuctionFilter filter, OrderSpecifier<String>[] orderSpecifiers)
  {
    JPAQuery<?> query = new JPAQuery<>(em);
    QUserAuction qUserAuction = QUserAuction.userAuction;

    BooleanBuilder whereClause = buildWhereClause(filter);

    return query.select(qUserAuction)
      .from(qUserAuction)
      .where(whereClause)
      .orderBy(orderSpecifiers)
      .fetch();
  }


  private BooleanBuilder buildWhereClause(UserAuctionFilter filter)
  {
    QUserAuction qUserAuction = QUserAuction.userAuction;
    BooleanBuilder whereClause = new BooleanBuilder();
    Long id = filter.getAuctionId();
    if(id != null)
    {
      whereClause.and(qUserAuction.auctionSession.Id.eq(id));
    }

    String owner = filter.getOwner();
    if(StringUtils.hasText(owner))
    {
      whereClause.and(qUserAuction.createdBy.eq(owner));
    }

    String status = filter.getStatus();
    if(StringUtils.hasText(status))
    {
      whereClause.and(qUserAuction.requestStatus.eq(status));
    }

    return whereClause;
  }

}