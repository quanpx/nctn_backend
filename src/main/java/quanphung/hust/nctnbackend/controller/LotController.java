package quanphung.hust.nctnbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;
import quanphung.hust.nctnbackend.service.LotInfoService;

@RestController
public class LotController implements LotOperations
{

  @Autowired
  private LotInfoService lotInfoService;


  @Override
  public void createLot(CreateLotInfoRequest request)
  {
    lotInfoService.createLotInfo(request);
  }

  @Override
  public void updateLot()
  {

  }

  @Override
  public void deleteLot()
  {

  }

  @Override
  public void searchLot()
  {

  }
}
