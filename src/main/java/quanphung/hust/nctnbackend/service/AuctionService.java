package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.GetAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.dto.response.AuctionDetailResponse;
import quanphung.hust.nctnbackend.dto.response.GetAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.GetUserAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateAuctionResponse;

public interface AuctionService
{
  void createAuction(CreateAuctionRequest request);

  GetAuctionResponse getAuctions(GetAuctionRequest request);

  ManipulateAuctionResponse registerAuction(Long auctionSession);

  AuctionDetailResponse getAuctionDetail(Long id);

  ManipulateAuctionResponse isRegistered(Long id);

  AuctionDetailResponse handleNext(Long id);

  void updateAuction(UpdateItemRequest request);

  AuctionDetailResponse handleEnd(Long id);

  GetUserAuctionResponse registeredAuctions();
}
