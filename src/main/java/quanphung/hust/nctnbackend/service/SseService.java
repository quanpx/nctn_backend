package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.EventDto;
import quanphung.hust.nctnbackend.dto.sse.BidMessage;

import java.util.Map;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
    void sendNotificationToAll(String type,String message);

    SseEmitter createEmitter();

    EventDto buildEvent(String type, String message);
}
