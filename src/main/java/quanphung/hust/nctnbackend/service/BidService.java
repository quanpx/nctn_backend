package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.SearchBidRequest;
import quanphung.hust.nctnbackend.dto.response.BidResponse;

public interface BidService {

    BidResponse bidLot(BidRequest request);
    BidResponse getAllBid(SearchBidRequest request);
}
