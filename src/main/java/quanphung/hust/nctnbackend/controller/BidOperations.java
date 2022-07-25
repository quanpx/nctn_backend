package quanphung.hust.nctnbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.response.BidResponse;

@RequestMapping(BidOperations.API_RESOURCE)
public interface BidOperations {
    String API_RESOURCE = "/api";
    String BID = "/bid";

    @PostMapping(BID)
    ResponseEntity<BidResponse> bidLot(@RequestBody BidRequest request);

    @GetMapping(BID)
    ResponseEntity<BidResponse> allBid(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "orderBy", required = false) String[] orderByColumns
    );

}
