package quanphung.hust.nctnbackend.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.Notificaiton;

@Repository
public interface NotificaitonRepository extends JpaRepository<Notificaiton, Long>
{
  @Query("select n from Notificaiton n order by n.createdBy")
  List<Notificaiton> findNotificaitonByOrderByCreatedByAsc();
}