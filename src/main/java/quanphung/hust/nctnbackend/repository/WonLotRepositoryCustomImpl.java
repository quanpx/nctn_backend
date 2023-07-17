package quanphung.hust.nctnbackend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import quanphung.hust.nctnbackend.domain.QLotInfo;
import quanphung.hust.nctnbackend.domain.QWonLot;
import quanphung.hust.nctnbackend.domain.WonLot;


public class WonLotRepositoryCustomImpl implements WonLotRepositoryCustom
{
  @PersistenceContext
  private EntityManager em;

  @Override
  @Transactional
  public List<WonLot> findWonLots(String username, Long Id)
  {
    JPAQuery<?> query = new JPAQuery<>(em);
    QWonLot qWonLot = QWonLot.wonLot;

    BooleanBuilder whereClause = new BooleanBuilder();
    if(StringUtils.hasText(username))
    {
      whereClause.and(qWonLot.owner.eq(username));

    }
    if(Id != null)
    {
      whereClause.and(qWonLot.session.Id.eq(Id));
    }

    return query.select(qWonLot)
      .from(qWonLot)
      .where(whereClause)
      .groupBy(qWonLot.session,qWonLot.id)
      .fetch();
  }
}
