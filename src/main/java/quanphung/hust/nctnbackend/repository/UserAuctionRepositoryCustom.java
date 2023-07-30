package quanphung.hust.nctnbackend.repository;

import java.util.List;

import com.querydsl.core.types.OrderSpecifier;
import quanphung.hust.nctnbackend.domain.UserAuction;
import quanphung.hust.nctnbackend.dto.filter.UserAuctionFilter;

public interface UserAuctionRepositoryCustom
{

    List<UserAuction> findAllByFilterAndOrders(UserAuctionFilter filter, OrderSpecifier<String>[] orderSpecifiers);

}