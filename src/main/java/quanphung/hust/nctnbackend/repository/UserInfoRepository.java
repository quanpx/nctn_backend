package quanphung.hust.nctnbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import quanphung.hust.nctnbackend.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>
{
}