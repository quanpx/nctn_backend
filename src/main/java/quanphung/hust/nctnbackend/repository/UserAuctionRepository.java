package quanphung.hust.nctnbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.UserAuction;

@Repository
public interface UserAuctionRepository extends JpaRepository<UserAuction, Long>
{
}