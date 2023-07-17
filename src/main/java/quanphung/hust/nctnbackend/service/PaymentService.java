package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.response.InvoiceResponse;
import quanphung.hust.nctnbackend.dto.response.PaymentResponse;

public interface PaymentService
{
  InvoiceResponse getInvoice(Long auctionId, String username);

  PaymentResponse pay(Long id);
}
