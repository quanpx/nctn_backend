package quanphung.hust.nctnbackend.mapping;

import org.springframework.stereotype.Component;

import quanphung.hust.nctnbackend.domain.Notificaiton;
import quanphung.hust.nctnbackend.dto.NotiDto;

@Component
public class NotiMapping implements BaseMapping<Notificaiton, NotiDto>
{
  @Override
  public NotiDto convertToDto(Notificaiton notificaiton)
  {
    return NotiDto.builder()
      .id(notificaiton.getId())
      .content(notificaiton.getContent())
      .from(notificaiton.getFrom())
      .to(notificaiton.getTo())
      .isRead(notificaiton.isRead())
      .type(notificaiton.getType())
      .build();
  }

  @Override
  public Notificaiton convertToEntity(NotiDto notiDto)
  {
    return null;
  }
}
