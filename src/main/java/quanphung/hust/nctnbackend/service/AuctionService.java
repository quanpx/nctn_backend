package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;

public interface AuctionService
{
  void createAuction(CreateAuctionRequest request);
}
