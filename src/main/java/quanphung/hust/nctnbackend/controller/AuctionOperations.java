package quanphung.hust.nctnbackend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.hibernate.action.internal.CollectionUpdateAction;
import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;

@RequestMapping(AuctionOperations.API_RESOURCE)
public interface AuctionOperations
{
  String API_RESOURCE = "/api";
  String AUCTION_RESOURCE = "/auction";

  @PostMapping(AUCTION_RESOURCE)
  void createAuction(@RequestBody CreateAuctionRequest request);

  @PutMapping(AUCTION_RESOURCE)
  void updateAuction(@RequestBody UpdateItemRequest request);

  void deleteItem();
  void searchItem();
}
