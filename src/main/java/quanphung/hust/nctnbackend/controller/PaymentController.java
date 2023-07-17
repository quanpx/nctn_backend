package quanphung.hust.nctnbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import quanphung.hust.nctnbackend.dto.response.InvoiceResponse;
import quanphung.hust.nctnbackend.dto.response.PaymentResponse;
import quanphung.hust.nctnbackend.service.PaymentService;

@RestController
public class PaymentController implements PaymentOperations
{
  @Autowired
  private PaymentService paymentService;

  @Override
  public ResponseEntity<InvoiceResponse> getInvoice(Long auction)
  {
    return ResponseEntity.ok(paymentService.getInvoice(auction,null ));
  }

  @Override
  public ResponseEntity<PaymentResponse> pay(Long id)
  {
    return ResponseEntity.ok(paymentService.pay(id));
  }
}
