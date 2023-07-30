package quanphung.hust.nctnbackend.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.OrderSpecifier;
import lombok.extern.slf4j.Slf4j;
import quanphung.hust.nctnbackend.config.AsyncTaskConfig;
import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.domain.InitializationInfo;
import quanphung.hust.nctnbackend.domain.Invoice;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.domain.UserAuction;
import quanphung.hust.nctnbackend.domain.UserInfo;
import quanphung.hust.nctnbackend.domain.WonLot;
import quanphung.hust.nctnbackend.dto.AuctionDTO;
import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.dto.UserAuctionDto;
import quanphung.hust.nctnbackend.dto.email.EmailDetails;
import quanphung.hust.nctnbackend.dto.filter.AuctionFilter;
import quanphung.hust.nctnbackend.dto.filter.LotFilter;
import quanphung.hust.nctnbackend.dto.filter.UserAuctionFilter;
import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.GetAuctionRequest;
import quanphung.hust.nctnbackend.dto.request.UpdateItemRequest;
import quanphung.hust.nctnbackend.dto.response.AuctionDetailResponse;
import quanphung.hust.nctnbackend.dto.response.GetAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.GetUserAuctionResponse;
import quanphung.hust.nctnbackend.dto.response.ManipulateAuctionResponse;
import quanphung.hust.nctnbackend.exception.InvalidSortColumnException;
import quanphung.hust.nctnbackend.exception.InvalidSortOrderException;
import quanphung.hust.nctnbackend.mapping.AuctionMapping;
import quanphung.hust.nctnbackend.mapping.LotMapping;
import quanphung.hust.nctnbackend.mapping.UserAuctionMapping;
import quanphung.hust.nctnbackend.repository.AuctionSessionRepository;
import quanphung.hust.nctnbackend.repository.BidInfoRepository;
import quanphung.hust.nctnbackend.repository.InvoiceRepository;
import quanphung.hust.nctnbackend.repository.LotInfoRepository;
import quanphung.hust.nctnbackend.repository.UserAuctionRepository;
import quanphung.hust.nctnbackend.repository.UserInfoRepository;
import quanphung.hust.nctnbackend.repository.WonLotRepository;
import quanphung.hust.nctnbackend.repository.orderutils.AuctionOrderUtils;
import quanphung.hust.nctnbackend.repository.orderutils.LotOrderUtils;
import quanphung.hust.nctnbackend.repository.orderutils.UserAuctionOrderUtils;
import quanphung.hust.nctnbackend.socket.controller.MessageController;
import quanphung.hust.nctnbackend.socket.message.Message;
import quanphung.hust.nctnbackend.socket.services.SocketService;
import quanphung.hust.nctnbackend.type.AuctionRequestStatus;
import quanphung.hust.nctnbackend.type.SessionStatus;
import quanphung.hust.nctnbackend.utils.SecurityUtils;

@Service
@Slf4j
public class AuctionServiceImpl implements AuctionService
{
  private static final String SYSTEM_NAME = "nctn_system";
  @Autowired
  private AuctionMapping mapping;

  @Autowired
  private LotMapping lotMapping;

  @Autowired
  private UserAuctionMapping userAuctionMapping;

  @Autowired
  private LotInfoRepository lotInfoRepository;

  @Autowired
  private AuctionSessionRepository auctionSessionRepository;

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private UserAuctionRepository userAuctionRepository;

  @Autowired
  private BidInfoRepository bidInfoRepository;

  @Autowired
  private WonLotRepository wonLotRepository;

  @Autowired
  private SseService sseService;

  @Autowired
  private MailService mailService;

  @Autowired
  private SocketService socketService;

  @Autowired
  private SimpUserRegistry simpUserRegistry;

  @Autowired
  private InvoiceRepository invoiceRepository;

  @Autowired
  @Qualifier(AsyncTaskConfig.TASK_EXECUTOR_BEAN)
  private AsyncTaskExecutor executor;

  @Override
  @Transactional
  public void createAuction(CreateAuctionRequest request)
  {
    List<Long> itemIds = request.getItemIds();
    List<LotInfo> items = lotInfoRepository.findAllById(itemIds);

    AuctionSession session = AuctionSession.builder()
      .name(request.getName())
      .description(request.getDescription())
      .startTime(new Timestamp(request.getStartTime()))
      .itemsInSession(items)
      .isStream(request.isStream())
      .streamLink(request.getStreamLink())
      .status(SessionStatus.CREATED.getStatus())
      .imageUrl(request.getImageUrl())
      .currLot(1)
      .nextLot(2)
      .numItem(items.size())
      .build();

    auctionSessionRepository.save(session);

    updateItemInSession(session, items);

  }

  @Override
  public GetAuctionResponse getAuctions(GetAuctionRequest request)
  {
    Integer page = request.getPage();
    Integer size = request.getSize();
    GetAuctionResponse response;
    AuctionFilter filter = AuctionFilter.builder()
      .createdAt(request.getCreatedAt())
      .startTime(request.getStartTime())
      .status(request.getStatus())
      .text(request.getText())
      .build();
    try
    {
      OrderSpecifier<String>[] orderSpecifiers = AuctionOrderUtils.createOrderBy(request.getOrderByColumns());
      List<AuctionSession> auctionSessions = auctionSessionRepository.findAuctionByPagingAndFilter(size, page, filter,
        orderSpecifiers);
      List<AuctionDTO> auctionDTOList = auctionSessions.stream()
        .map(item -> mapping.convertToDto(item))
        .collect(Collectors.toList());
      Long count = auctionSessionRepository.countAuctionByPagingAndFilter(page, size, filter);

      response = GetAuctionResponse.builder()
        .count(count)
        .auctionDTOList(auctionDTOList)
        .build();
    }
    catch (InvalidSortColumnException | InvalidSortOrderException e)
    {
      log.error(e.getMessage());
      throw new BadRequestException("Invalid sort column!");
    }

    return response;
  }

  @Override
  @Transactional
  public ManipulateAuctionResponse registerAuction(Long auctionSession)
  {
    ManipulateAuctionResponse response = new ManipulateAuctionResponse();

    String username = SecurityUtils.getCurrentUsername().orElse("datn");
    Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(auctionSession);
    if (auctionOpt.isPresent())
    {
      AuctionSession session = auctionOpt.get();
      if (!isRegistered(auctionSession).isRegistered())
      {

        UserAuction userAuction = UserAuction
          .builder()
          .auctionSession(session)
          .requestStatus(AuctionRequestStatus.PENDING.getValue())
          .build();

        userAuctionRepository.save(userAuction);

        //update number of register
        session.increaseRegisterNumber();
        session.increasePendingRequest();
        session = auctionSessionRepository.save(session);

        response.setSuccess(true);
        Optional<UserInfo> userInfoOpt = userInfoRepository.findUserInfoByUsername(username);
        if (userInfoOpt.isPresent())
        {
          UserInfo userInfo = userInfoOpt.get();
          // Send email noti to user
          EmailDetails mail = EmailDetails.builder()
            .auctionName(session.getName())
            .auctionId(session.getId())
            .time(session.getStartTime().toString())
            .subject("Registered auction successfully")
            .msgBody("Registered auction successfully!")
            .recipient(userInfo.getEmail())
            .receiver(username)
            .build();
          mailService.sendMailWithThymeleaf(mail, "new-register-auction");
        }
      }
    }
    else
    {
      log.error("Not found auction!");
      response.setError("Auction not found!");
      response.setSuccess(false);
    }
    return response;
  }

  @Override
  public ManipulateAuctionResponse isRegistered(Long id)
  {
    ManipulateAuctionResponse response = new ManipulateAuctionResponse();

    Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(id);
    UserAuction userAuction = null;
    if (auctionOpt.isPresent())
    {
      AuctionSession auctionSession = auctionOpt.get();
      String username = SecurityUtils.getCurrentUsername().orElse(null);
      userAuction = userAuctionRepository.findUserAuctionByCreatedByAndAuctionSession(username,
        auctionSession);

      boolean registered = userAuction != null;

      response.setRegistered(registered);
      response.setStatus(auctionSession.getStatus());
    }

    return response;

  }

  @Override
  @Transactional
  public AuctionDetailResponse handleNext(Long id)
  {
    Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(id);
    AuctionDetailResponse response = new AuctionDetailResponse();
    if (auctionOpt.isPresent())
    {
      AuctionSession auction = auctionOpt.get();
      Integer size = auction.getNumItem();
      Integer nextIdx = auction.getNextLot();

      auction.setCurrLot(nextIdx);
      List<LotInfo> lotInfos = auction.getItemsInSession();

      auction.setNextLot(getNextIndex(lotInfos, nextIdx));

      auction = auctionSessionRepository.save(auction);

      response.setNextLot(auction.getNextLot());
      response.setCurrLot(auction.getCurrLot());
      response.setAuctionDTO(mapping.convertToDto(auction));

      Optional<LotInfo> lotInfoOpt = lotInfoRepository.findByOrderInSessionAndSession(auction.getCurrLot(), auction);
      if (lotInfoOpt.isPresent())
      {
        LotInfo lotInfo = lotInfoOpt.get();
        Message message = Message.builder()
          .message("Next lot")
          .type("next-lot")
          .nextLot(auction.getNextLot())
          .currLot(lotMapping.convertToDto(lotInfo))
          .build();

        socketService.sendToAll(message, MessageController.TO_SPECIFIC_USER, null);
      }

    }
    return response;
  }

  @Override
  @Transactional
  public void updateAuction(UpdateItemRequest request)
  {
    ObjectMapper objectMapper = new ObjectMapper();

    Long id = request.getId();
    Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(id);
    if (auctionOpt.isPresent())
    {
      AuctionSession auction = auctionOpt.get();
      if (StringUtils.hasText(request.getStatus()))
      {
        String status = request.getStatus();
        auction.setStatus(status);
        if (status.equals("start"))
        {
          auction.setStream(true);
          auction.setCurrLot(1);
          auction.setNextLot(2);

          auction = auctionSessionRepository.save(auction);

          Map<String, String> registeredEmailList = findAllRegisterEmail(auction);
          EmailDetails emailDetails = EmailDetails.builder()
            .auctionName(auction.getName())
            .subject("Thông báo từ phiên đấu giá")
            .auctionId(auction.getId())
            .build();

          sendStatusChangeEmail(registeredEmailList, emailDetails);
        }
        else if (status.equals("end"))
        {
          handleEnd(auction);
        }
      }

    }
  }

  private void sendStatusChangeEmail(Map<String, String> emails, EmailDetails emailDetails)
  {
    for (Map.Entry<String, String> email : emails.entrySet())
    {
      log.info("Send email to " + email.getValue());
      emailDetails.setRecipient(email.getValue());
      emailDetails.setReceiver(email.getKey());
      mailService.sendMailWithThymeleaf(emailDetails, "auction-started-email");
    }

  }

  private Map<String, String> findAllRegisterEmail(AuctionSession auction)
  {
    List<UserAuction> userAuctions = userAuctionRepository.findUserAuctionByAuctionSession(auction);
    Map<String, String> registeredEmails = new HashMap<>();
    if (!userAuctions.isEmpty())
    {
      List<String> users = userAuctions.stream().map(InitializationInfo::getCreatedBy).collect(Collectors.toList());
      for (String user : users)
      {
        Optional<UserInfo> userOpt = userInfoRepository.findUserInfoByUsername(user);
        if (userOpt.isPresent())
        {
          UserInfo userInfo = userOpt.get();
          String email = userInfo.getEmail();
          registeredEmails.put(user, email);

        }
      }
    }
    return registeredEmails;
  }

  private AuctionDetailResponse handleEnd(AuctionSession auction)
  {
    Message message = Message.builder()
      .message("Phiên đấu giá đã kết thúc.")
      .type("auction-end")
      .build();

    socketService.sendToAll(message, MessageController.TO_SPECIFIC_USER, null);

    executor.execute(() -> sendEmailAfterAuction(auction));

    createInvoice(auction);

    return null;
  }

  private void createInvoice(AuctionSession auction)
  {
    List<String> users = lotInfoRepository.getWonUsers(auction.getId());
    Long auctionId = auction.getId();
    for (String user : users)
    {
      if (StringUtils.hasText(user))
      {
        List<WonLot> wonLots = wonLotRepository.findWonLots(user, auctionId);

        List<LotInfo> lotInfos = wonLots.stream().map(WonLot::getLot).collect(Collectors.toList());
        Long total = lotInfos.stream()
          .map(LotInfo::getSoldPrice)
          .reduce(0L, Long::sum);

        Invoice invoice = Invoice.builder()
          .isPaid(false)
          .total(total)
          .customer(user)
          .session(auction)
          .build();

        for (LotInfo lot : lotInfos)
        {
          invoice.addLotId(lot.getId());
        }

        invoice = invoiceRepository.save(invoice);

        invoice = invoiceRepository.saveAndFlush(invoice);

      }

    }
  }

  @Async
  protected void sendEmailAfterAuction(AuctionSession auction)
  {
    log.info("Running on thread - " + Thread.currentThread().getName());
    //
    List<String> users = lotInfoRepository.getWonUsers(auction.getId());

    for (String user : users)
    {
      Optional<UserInfo> userOpt = userInfoRepository.findUserInfoByUsername(user);
      if (userOpt.isPresent())
      {
        UserInfo userInfo = userOpt.get();
        String emailTo = userInfo.getEmail();
        LotFilter filter = LotFilter.builder()
          .soldFor(user)
          .build();
        try
        {
          OrderSpecifier<String>[] orderSpecifiers = LotOrderUtils.createOrderBy(null);
          List<LotInfo> wonLots = lotInfoRepository.findLotInfoByPagingAndFilter(null, null, filter, orderSpecifiers);

          List<LotInfoDto> lotInfoDtoList = wonLots.stream()
            .map(lotInfo -> lotMapping.convertToDto(lotInfo))
            .collect(Collectors.toList());

          EmailDetails email = EmailDetails.builder()
            .subject("Sản phẩm đã thắng")
            .receiver(user)
            .auctionName(auction.getName())
            .recipient(emailTo)
            .wonLots(lotInfoDtoList)
            .build();

          mailService.sendMailWithThymeleaf(email, "won-lot-total");
        }
        catch (InvalidSortColumnException | InvalidSortOrderException e)
        {
          log.error(e.getMessage());
          throw new BadRequestException("Invalid sort column!");
        }

      }
    }
  }

  @Override
  public GetUserAuctionResponse registeredAuctions()
  {
    String username = SecurityUtils.getCurrentUsername().orElse("nctn-admin");
    List<UserAuction> userAuctions = userAuctionRepository.findAllByCreatedBy(username);
    List<UserAuctionDto> auctionDTOList = userAuctions.stream()
      .map(aution -> userAuctionMapping.convertToDto(aution))
      .collect(Collectors.toList());

    return GetUserAuctionResponse.builder()
      .userAuctionDtoList(auctionDTOList).build();
  }

  @Override
  public AuctionDetailResponse handleJoin(Long id)
  {
    Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(id);
    AuctionDetailResponse response = new AuctionDetailResponse();
    String username = SecurityUtils.getCurrentUsername().orElse("system");

    if (auctionOpt.isPresent())
    {
      AuctionSession auction = auctionOpt.get();

      List<LotInfo> lotInfos = auction.getItemsInSession();

      response.setNextLot(auction.getNextLot());
      response.setCurrLot(auction.getCurrLot());
      response.setAuctionDTO(mapping.convertToDto(auction));

      Message message = Message.builder()
        .message("New join")
        .type("new-join")
        .users(simpUserRegistry.getUserCount())
        .build();

      socketService.sendToAll(message, MessageController.TO_SPECIFIC_USER, null);
    }
    return response;
  }

  @Override
  @Transactional
  public ManipulateAuctionResponse approveRequest(Long id,boolean approve)
  {
    ManipulateAuctionResponse response = new ManipulateAuctionResponse();
    Optional<UserAuction> userAuctionOpt = userAuctionRepository.findById(id);

    if (userAuctionOpt.isPresent())
    {
      UserAuction userAuction = userAuctionOpt.get();

      userAuction.setRequestStatus(approve ? AuctionRequestStatus.APPROVED.getValue(): AuctionRequestStatus.REJECT.getValue());

      userAuction = userAuctionRepository.save(userAuction);

      String sender = userAuction.getCreatedBy();

      Optional<UserInfo> userInfoOpt = userInfoRepository.findUserInfoByUsername(sender);

      if (userInfoOpt.isPresent())
      {
        UserInfo userInfo = userInfoOpt.get();

        String emailTo = userInfo.getEmail();
        Long auctionId = userAuction.getAuctionSession().getId();
        String auctionName = userAuction.getAuctionSession().getName();
        EmailDetails emailDetails = EmailDetails.builder()
          .auctionId(auctionId)
          .auctionName(auctionName)
          .subject("Xác nhận yêu cầu tham gia phiên đấu giá")
          .receiver(sender)
          .recipient(emailTo)
          .build();

        String template = approve ? "approve-request" : "reject-request";
        executor.execute(() -> sendEmailConfirm(emailDetails, template));
      }

      AuctionSession auction = userAuction.getAuctionSession();

      auction.decreasePendingRequest();

      auction = auctionSessionRepository.save(auction);

    }
    response.setSuccess(true);
    return response;
  }

  @Override
  public AuctionDetailResponse getAllRequests(UserAuctionFilter filter)
  {
    AuctionDetailResponse response = null;
    Long auctionId = filter.getAuctionId();

    Optional<AuctionSession> auctionSessionOpt = auctionSessionRepository.findById(auctionId);
    if (auctionSessionOpt.isPresent())
    {
      AuctionSession auction = auctionSessionOpt.get();
      try
      {
        OrderSpecifier<String>[] orderSpecifiers = UserAuctionOrderUtils.createOrderBy(null);
        List<UserAuction> userAuctions = userAuctionRepository.findAllByFilterAndOrders(filter, orderSpecifiers);
        if (!userAuctions.isEmpty())
        {
          List<UserAuctionDto> userAuctionDtos = userAuctions.stream()
            .map(ua -> userAuctionMapping.convertToDto(ua))
            .collect(Collectors.toList());

          response = AuctionDetailResponse.builder()
            .userAuctionDtos(userAuctionDtos)
            .build();
        }
        else
        {
          response = AuctionDetailResponse.builder()
            .userAuctionDtos(new ArrayList<>())
            .build();
        }
      }
      catch (InvalidSortOrderException | InvalidSortColumnException e)
      {
        throw new RuntimeException(e);
      }

    }

    return response;
  }

  @Async
  protected void sendEmailConfirm(EmailDetails email, String template)
  {
    mailService.sendMailWithThymeleaf(email, template);
  }

  private Integer getNextIndex(List<LotInfo> lotInfos, Integer currIdx)
  {

    int index = currIdx;
    int size = lotInfos.size();
    while (true)
    {
      if (index + 1 >= size)
      {
        index = 1;
      }
      else
      {
        index = index + 1;
      }
      LotInfo lotInfo = lotInfos.get(index - 1);
      if (!lotInfo.isSold())
      {
        return index;
      }

    }
  }

  @Override
  @Transactional
  public AuctionDetailResponse getAuctionDetail(Long id)
  {
    Optional<AuctionSession> auctionOpt = auctionSessionRepository.findById(id);
    if (auctionOpt.isPresent())
    {
      AuctionSession auction = auctionOpt.get();
      AuctionDetailResponse response = new AuctionDetailResponse();
      AuctionDTO auctionDTO = mapping.convertToDto(auction);
      List<LotInfo> lotInfos = auction.getItemsInSession();
      List<LotInfoDto> lotInfoDtoList = lotInfos.stream()
        .map(lotInfo -> lotMapping.convertToDto(lotInfo))
        .collect(Collectors.toList());

      response.setAuctionDTO(auctionDTO);
      response.setLotInfos(lotInfoDtoList);
      response.setCurrLot(auction.getCurrLot());
      response.setNextLot(auction.getNextLot());
      ;
      return response;
    }
    else
    {
      log.warn("Not found auction");
      throw new BadRequestException("Not found auction");
    }

  }

  private void updateItemInSession(AuctionSession session, List<LotInfo> items)
  {
    int order = 1;

    for (LotInfo item : items)
    {
      item.setSession(session);
      item.setOrderInSession(order);

      item = lotInfoRepository.save(item);
      order++;
    }

    session = auctionSessionRepository.save(session);

  }
}
