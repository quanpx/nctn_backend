package quanphung.hust.nctnbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.LikedItem;
import quanphung.hust.nctnbackend.domain.LotInfo;

@Repository
public interface LikedItemRepository extends JpaRepository<LikedItem, Long>
{
  List<LikedItem> findLikedItemByCreatedBy(String createdBy);

  Optional<LikedItem> findLikedItemByCreatedByAndAndLotInfo(String createdBy, LotInfo lotInfo);
}