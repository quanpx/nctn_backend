package quanphung.hust.nctnbackend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import quanphung.hust.nctnbackend.domain.BidInfo;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.domain.QBidInfo;
import quanphung.hust.nctnbackend.dto.filter.BidFilter;
import quanphung.hust.nctnbackend.utils.DataFilterUtils;

@Repository
public class BidRepositoryCustomImpl implements BidRepositoryCustom
{
  @PersistenceContext
  private EntityManager em;

  @Override
  @Transactional
  public List<BidInfo> findBidInfoByUsernameAndLot(String username, Long lotId)
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
      .fetch();
  }

  @Override
  public List<BidInfo> findBidInfoByFilterAndPaging(BidFilter filter, OrderSpecifier<String>[] orderSpecifiers, Integer page, Integer size) {
    size = DataFilterUtils.resolveSize(size);
    page = DataFilterUtils.resolvePage(page);

    BooleanBuilder whereClause = buildWhereClause(filter);
    JPAQuery<?> query = new JPAQuery<>(em);
    QBidInfo qBidInfo = QBidInfo.bidInfo;

    return query.select(qBidInfo)
            .from(qBidInfo)
            .where(whereClause)
            .limit(size)
            .offset((long)page*size)
            .fetch();
  }

  @Override
  public long countBidInfoByFilterAndPaging(BidFilter filter, Integer page, Integer size) {
    size = DataFilterUtils.resolveSize(size);
    page = DataFilterUtils.resolvePage(page);

    BooleanBuilder whereClause = buildWhereClause(filter);
    JPAQuery<?> query = new JPAQuery<>(em);
    QBidInfo qBidInfo = QBidInfo.bidInfo;

    return query.select(qBidInfo)
            .from(qBidInfo)
            .where(whereClause)
            .limit(size)
            .offset((long)page*size)
            .fetchCount();
  }

  @Override
  public List<BidInfo> findAllByLotInfo(LotInfo lotInfo)
  {
    JPAQuery<?> query = new JPAQuery<>(em);
    QBidInfo qBidInfo = QBidInfo.bidInfo;
    OrderSpecifier<String> orderSpecifier= new OrderSpecifier<>(Order.DESC, qBidInfo.createdDate.stringValue());
    return query.select(qBidInfo)
      .from(qBidInfo)
      .where(qBidInfo.lotInfo.id.eq(lotInfo.getId()))
      .orderBy(orderSpecifier)
      .limit(10)
      .fetch();
  }

  private BooleanBuilder buildWhereClause(BidFilter filter) {

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QBidInfo qBidInfo = QBidInfo.bidInfo;
    String username = filter.getUsername();
    if(username!=null)
    {
      booleanBuilder.and(qBidInfo.createdBy.eq(username));
    }
    String status = filter.getStatus();
    if(status!=null)
    {
      booleanBuilder.and(qBidInfo.status.eq(status));
    }
    return booleanBuilder;
  }

}
