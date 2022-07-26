package quanphung.hust.nctnbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import quanphung.hust.nctnbackend.dto.sse.BidMessage;
import quanphung.hust.nctnbackend.service.SseService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@Slf4j
public class SseEmitterController implements SseEmitterOperations {
    public static List<SseEmitter> emitters=new CopyOnWriteArrayList<>();

    @Autowired
    private SseService sseService;

    @Override
    public SseEmitter subscribe() {
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
    public void dispatch() {
        BidMessage message = BidMessage.builder()
                .currentPrice("1000")
                .message("Have bid 1000s")
                .build();
        sseService.dispatchEvent(message);
    }
}
