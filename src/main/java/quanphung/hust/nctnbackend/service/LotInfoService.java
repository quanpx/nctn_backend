package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;
import quanphung.hust.nctnbackend.dto.request.GetLotRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.dto.response.GetLotResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateBidResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateLotResponse;

public interface LotInfoService
{
  void createLotInfo(CreateLotInfoRequest request);
  void updateLotInfo(UpdateItemRequest request);

  LotInfoDto getLotDetail(Long id);

  GetLotResponse getLotInfos(GetLotRequest request);

  ManipulateLotResponse markSold(BidRequest request);
}
