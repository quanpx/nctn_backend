package quanphung.hust.nctnbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.repository.LotInfoRepository;

@Service
public class LotInfoServiceImpl implements LotInfoService
{
  @Autowired
  private LotInfoRepository lotInfoRepository;

  @Override
  public void createLotInfo(CreateLotInfoRequest request)
  {
    LotInfo item = convertRequest(request);
    lotInfoRepository.save(item);
  }

  private LotInfo convertRequest(CreateLotInfoRequest request)
  {
    return LotInfo.builder()
      .name(request.getName())
      .description(request.getDescription())
      .step(request.getStep())
      .estmPrice(request.getEstmPrice())
      .currentPrice(request.getCurrentPrice())
      .build();
  }

  @Override
  public void updateLotInfo(UpdateItemRequest request)
  {

  }
}
