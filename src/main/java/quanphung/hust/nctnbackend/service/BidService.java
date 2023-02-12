package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.SearchBidRequest;
import quanphung.hust.nctnbackend.dto.response.BidResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateBidResponse;

public interface BidService
{

  BidResponse bidLot(BidRequest request);

  BidResponse getAllBid(SearchBidRequest request);

  ManipulateBidResponse isBid(Long lotId);

  BidResponse getBidInAuction(Long lotId);
}
