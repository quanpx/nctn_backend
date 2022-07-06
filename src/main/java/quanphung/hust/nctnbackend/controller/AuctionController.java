package quanphung.hust.nctnbackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.GetAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.dto.response.AuctionStatusResponse;
import quanphung.hust.nctnbackend.dto.response.BidResponse;
import quanphung.hust.nctnbackend.dto.response.GetAuctionResponse;
import quanphung.hust.nctnbackend.service.AuctionService;
import quanphung.hust.nctnbackend.type.SessionStatus;

@RestController
public class AuctionController implements AuctionOperations
{
  @Autowired
  private AuctionService auctionService;

  @Override
  public void createAuction(CreateAuctionRequest request)
  {
    auctionService.createAuction(request);
  }

  @Override
  public void updateAuction(UpdateItemRequest request)
  {

  }

  @Override
  public ResponseEntity<GetAuctionResponse> getAuctions(
    Integer page,
    Integer size,
    Long createdAt,
    Long startTime,
    String status,
    String[] orderByColumns)
  {
    GetAuctionRequest request = GetAuctionRequest.builder()
      .size(size)
      .page(page)
      .startTime(startTime)
      .createdAt(createdAt)
      .status(status)
      .orderByColumns(orderByColumns)
      .build();
    return ResponseEntity.ok(auctionService.getAuctions(request));
  }

  @Override
  public void registerAuction(Long auctionId)
  {
    auctionService.registerAuction(auctionId);
  }

  @Override
  public ResponseEntity<AuctionStatusResponse> getAuctionStatus()
  {
    AuctionStatusResponse response = new AuctionStatusResponse();
    List<String> statusList = new ArrayList<>();
    for (SessionStatus status : SessionStatus.values())
    {
      statusList.add(status.getStatus());
    }
    response.setStatus(statusList);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<BidResponse> bidLot(BidRequest request)
  {
    return ResponseEntity.ok(auctionService.bidLot(request));
  }

  @Override
  public void deleteItem()
  {

  }

  @Override
  public void searchItem()
  {

  }

}
