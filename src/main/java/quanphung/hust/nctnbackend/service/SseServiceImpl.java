package quanphung.hust.nctnbackend.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;
import quanphung.hust.nctnbackend.dto.EventDto;
import quanphung.hust.nctnbackend.mapping.EventMapping;

@Service
@Slf4j
public class SseServiceImpl implements SseService
{

  public static List<SseEmitter> emitters=new CopyOnWriteArrayList<>();


  @Autowired
  private EventMapping eventMapping;


  @Override
  public void sendNotificationToAll(String type,String message)
  {
    EventDto event = buildEvent(type,message);
    for(SseEmitter emitter: emitters){
      doSendNotification(emitter,event);
    }
  }

  private void doSendNotification(SseEmitter emitter, EventDto event)
  {

      try
      {
        emitter.send(eventMapping.toSseEventBuilder(event));
      }
      catch (IOException | IllegalStateException e)
      {
        emitters.remove(emitter);
      }
  }

  @Override
  public SseEmitter createEmitter()
  {

    SseEmitter emitter = new SseEmitter();
    try
    {
      emitter.send(SseEmitter.event().name("Init"));
    }catch (IOException ex)
    {
      log.error(ex.getMessage());
    }
    emitter.onCompletion(()-> emitters.remove(emitter));
    emitter.onTimeout(()->emitters.remove(emitter));
    emitters.add(emitter);
    return emitter;
  }

  @Override
  public EventDto buildEvent(String type, String message)
  {
    return EventDto.builder()
      .type(type)
      .message(message)
      .build();
  }
}
