package quanphung.hust.nctnbackend.service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.dto.request.CreateAuctionRequest;
import quanphung.hust.nctnbackend.repository.AuctionSessionRepository;
import quanphung.hust.nctnbackend.repository.LotInfoRepository;

@Service
public class AuctionServiceImpl implements AuctionService
{
  @Autowired
  private LotInfoRepository lotInfoRepository;
  ;

  @Autowired
  private AuctionSessionRepository auctionSessionRepository;

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
      .build();

    auctionSessionRepository.save(session);

    updateItemInSession(session, items);

  }

  private void updateItemInSession(AuctionSession session, List<LotInfo> items)
  {
    int order = 1;
    int numSession = session.getItemsInSession().size();
    session.setNumItem(numSession);
    auctionSessionRepository.save(session);

    for (LotInfo item : items)
    {
      item.setSession(session);
      item.setOrderInSession(order);

      lotInfoRepository.save(item);
      order++;
    }
  }
}
