package quanphung.hust.nctnbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import quanphung.hust.nctnbackend.dto.response.InvoiceResponse;
import quanphung.hust.nctnbackend.dto.response.PaymentResponse;

@RequestMapping(PaymentOperations.API_RESOURCE)
public interface PaymentOperations
{
  String API_RESOURCE = "/api/payment";

  String INVOICE = "/invoice";

  String PAY = "/pay";

  @GetMapping(INVOICE)
  ResponseEntity<InvoiceResponse> getInvoice(@RequestParam(name = "auction",required = false) Long auction);

  @GetMapping(PAY)
  ResponseEntity<PaymentResponse> pay(@RequestParam(name = "id") Long id);
}
