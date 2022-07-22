package quanphung.hust.nctnbackend.mapping;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.dto.LotInfoDto;

@Component
public class LotMapping implements BaseMapping<LotInfo, LotInfoDto>
{
  @Override
  public LotInfoDto convertToDto(LotInfo lotInfo)
  {
    Timestamp startTime = null;
    String session = null;
    if (lotInfo.getSession() != null)
    {
      startTime = lotInfo.getSession().getStartTime();
      session = lotInfo.getSession().getId().toString();
    }

    return LotInfoDto.builder()
      .id(lotInfo.getId().toString())
      .name(lotInfo.getName())
      .description(lotInfo.getDescription())
      .orderLot(lotInfo.getOrderInSession())
      .estmPrice(lotInfo.getEstmPrice())
      .startTime(startTime)
      .currentPrice(lotInfo.getCurrentPrice())
      .session(session)
      .isSold(lotInfo.isSold())
      .soldPrice(lotInfo.getSoldPrice())
      .imageUrl(lotInfo.getImageUrl())
      .build();
  }

  @Override
  public LotInfo convertToEntity(LotInfoDto lotInfoDto)
  {
    return null;
  }
}
