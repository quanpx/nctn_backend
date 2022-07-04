package quanphung.hust.nctnbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.service.AuctionService;

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
  public void deleteItem()
  {

  }

  @Override
  public void searchItem()
  {

  }


}
