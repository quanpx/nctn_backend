package quanphung.hust.nctnbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.hibernate.action.internal.CollectionUpdateAction;
import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.dto.response.AuctionDetailResponse;
import quanphung.hust.nctnbackend.dto.response.AuctionStatusResponse;
import quanphung.hust.nctnbackend.dto.response.BidResponse;
import quanphung.hust.nctnbackend.dto.response.GetAuctionResponse;

@RequestMapping(AuctionOperations.API_RESOURCE)
public interface AuctionOperations
{
  String API_RESOURCE = "/api";
  String AUCTION_RESOURCE = "/auction";
  String REGISTER = "/register/{id}";

  String AUCTION_DETAIL = "/auction/{id}";

  @PostMapping(AUCTION_RESOURCE)
  void createAuction(@RequestBody CreateAuctionRequest request);

  @PutMapping(AUCTION_RESOURCE)
  void updateAuction(@RequestBody UpdateItemRequest request);

  @GetMapping(AUCTION_RESOURCE)
  ResponseEntity<GetAuctionResponse> getAuctions(
    @RequestParam(name = "page", required = false) Integer page,
    @RequestParam(name = "size", required = false) Integer size,
    @RequestParam(name = "created_at", required = false) Long createdAt,
    @RequestParam(name = "start_time", required = false) Long startTime,
    @RequestParam(name = "status", required = false) String status,
    @RequestParam(name = "orderBy", required = false) String[] orderByColumns
  );

  @GetMapping(AUCTION_RESOURCE+REGISTER)
  void registerAuction(@PathVariable(name = "id") Long auctionId);

  @GetMapping(AUCTION_RESOURCE+"/status")
  ResponseEntity<AuctionStatusResponse> getAuctionStatus();

  @PostMapping(AUCTION_RESOURCE+"/bid")
  ResponseEntity<BidResponse> bidLot(@RequestBody BidRequest request);
  void deleteItem();

  void searchItem();

  @GetMapping(AUCTION_DETAIL)
  ResponseEntity<AuctionDetailResponse> getAuctionDetail(@PathVariable(name = "id") Long id);
}
