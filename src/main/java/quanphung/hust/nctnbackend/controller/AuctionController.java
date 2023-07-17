package quanphung.hust.nctnbackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.GetAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.dto.response.AuctionDetailResponse;
import quanphung.hust.nctnbackend.dto.response.AuctionStatusResponse;
import quanphung.hust.nctnbackend.dto.response.GetAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.GetUserAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateAuctionResponse;
import quanphung.hust.nctnbackend.service.AuctionService;
import quanphung.hust.nctnbackend.type.SessionStatus;

@RestController
public class AuctionController implements AuctionOperations
{
  @Autowired
  private AuctionService auctionService;

  @Override
  public ResponseEntity<GetUserAuctionResponse> getRegisteredAuctions()
  {
    return ResponseEntity.ok(auctionService.registeredAuctions());
  }

  @Override
  public void createAuction(CreateAuctionRequest request)
  {
    auctionService.createAuction(request);
  }

  @Override
  public void updateAuction(UpdateItemRequest request)
  {
    auctionService.updateAuction(request);
  }

  @Override
  public ResponseEntity<GetAuctionResponse> getAuctions(
    Integer page,
    Integer size,
    Long createdAt,
    Long startTime,
    String status,
    String[] orderByColumns,
    String text)
  {
    GetAuctionRequest request = GetAuctionRequest.builder()
      .size(size)
      .page(page)
      .startTime(startTime)
      .createdAt(createdAt)
      .status(status)
      .orderByColumns(orderByColumns)
      .text(text)
      .build();
    return ResponseEntity.ok(auctionService.getAuctions(request));
  }

  @Override
  public ResponseEntity<ManipulateAuctionResponse> registerAuction(Long auctionId)
  {
    ManipulateAuctionResponse response = auctionService.registerAuction(auctionId);

    return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
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
  public ResponseEntity<ManipulateAuctionResponse> isRegistered(Long id)
  {
    return ResponseEntity.ok(auctionService.isRegistered(id));
  }

  @Override
  public void deleteItem()
  {

  }

  @Override
  public void searchItem()
  {

  }

  @Override
  public ResponseEntity<AuctionDetailResponse> getAuctionDetail(Long id)
  {
    return ResponseEntity.ok(auctionService.getAuctionDetail(id));
  }

  @Override
  public ResponseEntity<AuctionDetailResponse> handleNext(Long id)
  {
    return ResponseEntity.ok(auctionService.handleNext(id));
  }

  @Override
  public ResponseEntity<AuctionDetailResponse> handleEnd(Long id)
  {
    return null;
  }

  @Override
  public ResponseEntity<AuctionDetailResponse> handleJoin(Long id)
  {
    return ResponseEntity.ok(auctionService.handleJoin(id));
  }

}
