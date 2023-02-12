package quanphung.hust.nctnbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.response.BidResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateBidResponse;

@RequestMapping(BidOperations.API_RESOURCE)
public interface BidOperations
{
  String API_RESOURCE = "/api/bid";

  String IS_BID = "/isbid";

  String BID_IN_AUCTION ="/bidinauction";

  @PostMapping
  ResponseEntity<BidResponse> bidLot(@RequestBody BidRequest request);

  @GetMapping
  ResponseEntity<BidResponse> allBid(
    @RequestParam(name = "page", required = false) Integer page,
    @RequestParam(name = "size", required = false) Integer size,
    @RequestParam(name = "status", required = false) String status,
    @RequestParam(name = "orderBy", required = false) String[] orderByColumns
  );

  @GetMapping(BID_IN_AUCTION)
  ResponseEntity<BidResponse> getBidInAuction(
    @RequestParam(name = "lotId", required = false) Long lotId
  );

  @GetMapping(IS_BID)
  ResponseEntity<ManipulateBidResponse> isBid(@RequestParam(name = "lotId") Long lotId);

}
