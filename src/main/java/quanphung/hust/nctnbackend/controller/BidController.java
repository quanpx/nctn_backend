package quanphung.hust.nctnbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import quanphung.hust.nctnbackend.dto.request.BidRequest;
import quanphung.hust.nctnbackend.dto.request.SearchBidRequest;
import quanphung.hust.nctnbackend.dto.response.BidResponse;
import quanphung.hust.nctnbackend.service.BidService;
import quanphung.hust.nctnbackend.utils.SecurityUtils;

@RestController
public class BidController implements BidOperations{
    @Autowired
    private BidService bidService;

    @Override
    public ResponseEntity<BidResponse> bidLot(BidRequest request) {
        return ResponseEntity.ok(bidService.bidLot(request));
    }

    @Override
    public ResponseEntity<BidResponse> allBid(Integer page,
                                              Integer size,
                                              String status,
                                              String[] orderByColumns) {

        String username = SecurityUtils.getCurrentUsername().orElse("nctn-admin");
        SearchBidRequest request = SearchBidRequest.builder()
                .username(username)
                .page(page)
                .size(size)
                .status(status)
                .orderByColumns(orderByColumns)
                .build();
        return ResponseEntity.ok(bidService.getAllBid(request));
    }
}
