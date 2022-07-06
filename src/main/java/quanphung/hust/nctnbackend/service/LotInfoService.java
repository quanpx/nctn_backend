package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;
import quanphung.hust.nctnbackend.dto.request.GetLotRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.dto.response.GetLotResponse;

public interface LotInfoService
{
  void createLotInfo(CreateLotInfoRequest request);
  void updateLotInfo(UpdateItemRequest request);

  LotInfoDto getLotDetail(Long id);

  GetLotResponse getLotInfos(GetLotRequest request);
}
