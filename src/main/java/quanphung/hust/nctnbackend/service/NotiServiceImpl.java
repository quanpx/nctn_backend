package quanphung.hust.nctnbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import quanphung.hust.nctnbackend.domain.Notificaiton;
import quanphung.hust.nctnbackend.dto.NotiDto;
import quanphung.hust.nctnbackend.dto.response.NotiResponse;
import quanphung.hust.nctnbackend.mapping.NotiMapping;
import quanphung.hust.nctnbackend.repository.NotificaitonRepository;

@Service
@Slf4j
public class NotiServiceImpl implements NotiService
{
  @Autowired
  private NotificaitonRepository notificaitonRepository;

  @Autowired
  private NotiMapping notiMapping;
  @Override
  public NotiResponse getNotis()
  {

    List<Notificaiton> notificaitons = notificaitonRepository.findNotificaitonByOrderByCreatedByAsc();
    List<NotiDto> notiDtos = notificaitons
      .stream()
      .map(noti -> notiMapping.convertToDto(noti))
      .collect(Collectors.toList());

    return NotiResponse.builder()
      .notis(notiDtos)
      .count(notiDtos.size())
      .build();
  }
}
