package quanphung.hust.nctnbackend.mapping;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import quanphung.hust.nctnbackend.dto.EventDto;

@Component
@Slf4j
@AllArgsConstructor
public class EventMapping {

  public SseEmitter.SseEventBuilder toSseEventBuilder(EventDto event) {
    return SseEmitter.event()
      .id(RandomStringUtils.randomAlphanumeric(12))
      .name(event.getType())
      .data(event.getMessage());
  }
}