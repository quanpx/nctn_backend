package quanphung.hust.nctnbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import quanphung.hust.nctnbackend.controller.SseEmitterController;
import quanphung.hust.nctnbackend.dto.sse.BidMessage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SseServiceImpl implements SseService {
    
    @Override
    public void dispatchEvent(BidMessage data) {
       List<SseEmitter> subscribed = SseEmitterController.emitters;
        for (SseEmitter emitter:subscribed) {
            try {
                emitter.send(SseEmitter.event().name("haveBid").data(data));
            }catch (IOException ex)
            {
                log.error(ex.getMessage());
                subscribed.remove(emitter);
            }
        }
    }
}
