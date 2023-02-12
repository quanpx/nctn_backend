package quanphung.hust.nctnbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.domain.UserAuction;

@Repository
public interface UserAuctionRepository extends JpaRepository<UserAuction, Long>
{
    UserAuction findUserAuctionByCreatedByAndAuctionSession(String name, AuctionSession session);
    List<UserAuction> findAllByCreatedBy(String name);
}