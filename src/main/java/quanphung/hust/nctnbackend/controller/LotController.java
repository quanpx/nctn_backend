package quanphung.hust.nctnbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;
import quanphung.hust.nctnbackend.dto.request.GetLotRequest;
import quanphung.hust.nctnbackend.dto.response.GetLotResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateLotResponse;
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

  @Override
  public ResponseEntity<ManipulateLotResponse> markSold(BidRequest request)
  {
    ManipulateLotResponse response = lotInfoService.markSold(request);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<GetLotResponse> getLotInfos(
    Integer page,
    Integer size,
    String name,
    Boolean isSold,
    Long session,
    Long minPrice,
    Long maxPrice,
    String[] orderByColumns)
  {
    GetLotRequest request= GetLotRequest.builder()
      .name(name)
      .isSold(isSold)
      .orderByColumns(orderByColumns)
      .minPrice(minPrice)
      .maxPrice(maxPrice)
      .session(session)
      .page(page)
      .size(size)
      .build();
    return ResponseEntity.ok(lotInfoService.getLotInfos(request));
  }

  @Override
  public ResponseEntity<LotInfoDto> getLotDetail(Long id)
  {
    return ResponseEntity.ok(lotInfoService.getLotDetail(id));
  }

  @Override
  public ResponseEntity<ManipulateLotResponse> add2Favorite(Long id)
  {
    return ResponseEntity.ok(lotInfoService.add2Favorite(id));
  }
}
