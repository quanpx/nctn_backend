package quanphung.hust.nctnbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quanphung.hust.nctnbackend.dto.AuctionDTO;
import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.dto.response.AuctionDetailResponse;
import quanphung.hust.nctnbackend.dto.response.AuctionStatusResponse;
import quanphung.hust.nctnbackend.dto.response.GetAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.GetUserAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateAuctionResponse;

@RequestMapping(AuctionOperations.API_RESOURCE)
public interface AuctionOperations
{
  String API_RESOURCE = "/api/auction";

  String REGISTER = "/register";

  String AUCTION_STATUS = "/status";

  String AUCTION_DETAIL = "/{id}";

  String IS_REGISTERED = "/isregistered";

  String UPDATE_AUCTION_STATUS = "/updatestatus";

  String REGISTER_REQUEST = "/requests";
  String APPROVE_REGISTER_REQUEST = "/approve";

  String NEXT = "/next";

  String END = "/end";

  String JOIN = "/join";

  String REGISTER_AUCTION = "/register_auctions";

  @GetMapping(REGISTER_AUCTION)
  ResponseEntity<GetUserAuctionResponse> getRegisteredAuctions();

  @PostMapping
  void createAuction(@RequestBody CreateAuctionRequest request);

  @PostMapping(UPDATE_AUCTION_STATUS)
  void updateAuction(@RequestBody UpdateItemRequest request);

  @GetMapping
  ResponseEntity<GetAuctionResponse> getAuctions(
    @RequestParam(name = "page", required = false) Integer page,
    @RequestParam(name = "size", required = false) Integer size,
    @RequestParam(name = "created_at", required = false) Long createdAt,
    @RequestParam(name = "start_time", required = false) Long startTime,
    @RequestParam(name = "status", required = false) String status,
    @RequestParam(name = "orderBy", required = false) String[] orderByColumns,
    @RequestParam(name="text", required = false) String text);

  @GetMapping(REGISTER)
  ResponseEntity<ManipulateAuctionResponse> registerAuction(@RequestParam(name = "id") Long auctionId);

  @GetMapping(AUCTION_STATUS)
  ResponseEntity<AuctionStatusResponse> getAuctionStatus();

  @GetMapping(IS_REGISTERED)
  ResponseEntity<ManipulateAuctionResponse> isRegistered(@RequestParam(name = "id") Long id);

  void deleteItem();

  void searchItem();

  @GetMapping(REGISTER_REQUEST)
  ResponseEntity<AuctionDetailResponse> getAllRequests(
    @RequestParam(name="id",required = false) Long auctionId,
    @RequestParam(name="owner",required = false) String owner,
    @RequestParam(name="status", required = false) String status);

  @GetMapping(APPROVE_REGISTER_REQUEST)
  ResponseEntity<ManipulateAuctionResponse> approveRequest(
    @RequestParam(name = "id") Long id,
    @RequestParam(name="approve") boolean approve);

  @GetMapping(AUCTION_DETAIL)
  ResponseEntity<AuctionDetailResponse> getAuctionDetail(@PathVariable(name = "id") Long id);

  @GetMapping(NEXT)
  ResponseEntity<AuctionDetailResponse> handleNext(@RequestParam(name = "id") Long id);

  @GetMapping(END)
  ResponseEntity<AuctionDetailResponse> handleEnd(@RequestParam(name = "id") Long id);

  @GetMapping(JOIN)
  ResponseEntity<AuctionDetailResponse> handleJoin(@RequestParam(name="id") Long id);
}
