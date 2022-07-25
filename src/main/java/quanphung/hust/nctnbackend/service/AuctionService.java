package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.GetAuctionRequest;
import quanphung.hust.nctnbackend.dto.response.AuctionDetailResponse;
import quanphung.hust.nctnbackend.dto.response.BidResponse;
import quanphung.hust.nctnbackend.dto.response.GetAuctionResponse;

public interface AuctionService
{
  void createAuction(CreateAuctionRequest request);

  GetAuctionResponse getAuctions(GetAuctionRequest request);

  void registerAuction(Long auctionSession);

  AuctionDetailResponse getAuctionDetail(Long id);
}
