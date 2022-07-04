package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;

public interface LotInfoService
{
  void createLotInfo(CreateLotInfoRequest request);
  void updateLotInfo(UpdateItemRequest request);
}
