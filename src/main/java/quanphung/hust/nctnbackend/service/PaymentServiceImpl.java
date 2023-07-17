package quanphung.hust.nctnbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import quanphung.hust.nctnbackend.config.AsyncTaskConfig;
import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.domain.Invoice;
import quanphung.hust.nctnbackend.domain.UserInfo;
import quanphung.hust.nctnbackend.domain.WonLot;
import quanphung.hust.nctnbackend.dto.InvoiceDto;
import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.email.EmailDetails;
import quanphung.hust.nctnbackend.dto.response.InvoiceResponse;
import quanphung.hust.nctnbackend.dto.response.PaymentResponse;
import quanphung.hust.nctnbackend.exception.InvalidSortColumnException;
import quanphung.hust.nctnbackend.exception.InvalidSortOrderException;
import quanphung.hust.nctnbackend.mapping.InvoiceMapping;
import quanphung.hust.nctnbackend.repository.AuctionSessionRepository;
import quanphung.hust.nctnbackend.repository.InvoiceRepository;
import quanphung.hust.nctnbackend.repository.UserAuctionRepository;
import quanphung.hust.nctnbackend.repository.UserInfoRepository;
import quanphung.hust.nctnbackend.repository.WonLotRepository;
import quanphung.hust.nctnbackend.utils.SecurityUtils;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService
{
  @Autowired
  private InvoiceRepository invoiceRepository;

  @Autowired
  private AuctionSessionRepository auctionSessionRepository;

  @Autowired
  private InvoiceMapping invoiceMapping;

  @Autowired
  private WonLotRepository wonLotRepository;

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private MailService mailService;

  @Autowired
  @Qualifier(AsyncTaskConfig.TASK_EXECUTOR_BEAN)
  private AsyncTaskExecutor executor;

  @Override
  @Transactional
  public InvoiceResponse getInvoice(Long auctionId, String username)
  {
    InvoiceResponse response = new InvoiceResponse();
    String customer = !StringUtils.hasText(username) ? SecurityUtils.getCurrentUsername().orElse("datn") : username;
    Optional<AuctionSession> sessionOpt = auctionSessionRepository.findById(auctionId);
    if (sessionOpt.isPresent())
    {
      AuctionSession session = sessionOpt.get();
      Optional<Invoice> invoiceOpt = invoiceRepository.findInvoiceByCustomerAndSession(customer, session);

      if (invoiceOpt.isPresent())
      {
        Invoice invoice = invoiceOpt.get();
        InvoiceDto invoiceDto = invoiceMapping.convertToDto(invoice);
        response.setInvoice(invoiceDto);
      }

    }
    return response;
  }

  @Override
  public PaymentResponse pay(Long id)
  {
    PaymentResponse paymentResponse = new PaymentResponse();

    Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);
    if (invoiceOpt.isPresent())
    {
      Invoice invoice = invoiceOpt.get();

      invoice.setPaid(true);

      invoice = invoiceRepository.save(invoice);

      updatePaymentStatus(invoice.getCustomer(), invoice.getSession().getId());

      final Long auctionId = invoice.getSession().getId();
      final String receiver = invoice.getCustomer();

      executor.execute(() -> sendEmailConfirmPayment(auctionId, receiver));
      paymentResponse.setMessage("Thanh toán thành công.");
      paymentResponse.setStatus("Success");
    }
    else
    {
      paymentResponse.setMessage("Thanh toán lỗi.");
      paymentResponse.setStatus("Failed");
    }
    return paymentResponse;
  }

  private void updatePaymentStatus(String user, Long auctionId)
  {
    List<WonLot> wonLotList = wonLotRepository.findWonLots(user, auctionId);
    if (!wonLotList.isEmpty())
    {
      for (WonLot wonLot : wonLotList)
      {
        wonLot.setPaid(true);
        wonLot = wonLotRepository.save(wonLot);
      }
    }

  }

  @Async
  protected void sendEmailConfirmPayment(Long auctionId, String receiver)
  {
    log.info("Running on thread - " + Thread.currentThread().getName());
    //
    Optional<UserInfo> userOpt = userInfoRepository.findUserInfoByUsername(receiver);

    String emailTo = null;
    if (userOpt.isPresent())
    {
      UserInfo userInfo = userOpt.get();
      emailTo = userInfo.getEmail();
    }
    try
    {
      InvoiceResponse invoiceRes = getInvoice(auctionId, receiver);
      InvoiceDto invoiceDto = invoiceRes.getInvoice();
      String auctionName = invoiceDto.getAuctionDTO().getName();
      Set<LotInfoDto> wonLots = invoiceDto.getLots();
      EmailDetails email = EmailDetails.builder()
        .subject("Thông tin thanh toán")
        .receiver(receiver)
        .auctionName(auctionName)
        .recipient(emailTo)
        .wonLots(new ArrayList<>(wonLots))
        .total(invoiceDto.getTotal())
        .time(invoiceDto.getTime())
        .build();

      mailService.sendMailWithThymeleaf(email, "payment-confirm");
    }
    catch (Exception e)
    {
      throw e;
    }

  }
}
