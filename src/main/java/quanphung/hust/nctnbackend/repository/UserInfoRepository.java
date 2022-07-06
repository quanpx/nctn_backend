package quanphung.hust.nctnbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quanphung.hust.nctnbackend.domain.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>
{
  Optional<UserInfo> findUserInfoByUsername(String username);
}