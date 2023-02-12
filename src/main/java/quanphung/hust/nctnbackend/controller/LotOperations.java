package quanphung.hust.nctnbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.CreateLotInfoRequest;
import quanphung.hust.nctnbackend.dto.response.GetLotResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateLotResponse;

@RequestMapping(LotOperations.API_RESOURCE)
public interface LotOperations
{
  String API_RESOURCE = "/api";
  String LOT_RESOURCE ="/lot";

  String MARK_AS_SOLD = LOT_RESOURCE+"/sold";

  @PostMapping(LOT_RESOURCE)
  void createLot(@RequestBody CreateLotInfoRequest request);
  void updateLot();
  void deleteLot();
  void searchLot();

  @PutMapping(MARK_AS_SOLD)
  ResponseEntity<ManipulateLotResponse> markSold(@RequestBody BidRequest request);

  @GetMapping(LOT_RESOURCE)
  ResponseEntity<GetLotResponse> getLotInfos(
    @RequestParam(name = "page",required = false) Integer page,
    @RequestParam(name="size",required = false) Integer size,
    @RequestParam(name = "name",required = false) String name,
    @RequestParam(name = "isSold",required = false) Boolean isSold,
    @RequestParam(name = "session",required = false) Integer session,
    @RequestParam(name = "minEstmPrice",required = false) Long minEstmPrice,
    @RequestParam(name = "maxEstmPrice",required = false) Long maxEstmPrice,
    @RequestParam(name="orderBy" ,required = false) String[] orderByColumns
  );


  @GetMapping(LOT_RESOURCE+"/{id}")
  ResponseEntity<LotInfoDto> getLotDetail(@PathVariable(name = "id") Long id);

}
