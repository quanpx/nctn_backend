package quanphung.hust.nctnbackend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;

@RequestMapping(LotOperations.API_RESOURCE)
public interface LotOperations
{
  String API_RESOURCE = "/api";
  String LOT_RESOURCE ="/lot";

  @PostMapping(LOT_RESOURCE)
  void createLot(@RequestBody CreateLotInfoRequest request);
  void updateLot();
  void deleteLot();
  void searchLot();
}
