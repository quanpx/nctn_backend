package quanphung.hust.nctnbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
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


    @Autowired
    private SseService sseService;

    @Override
      public SseEmitter subscribeToEvents() {
        return sseService.createEmitter();
    }

}
