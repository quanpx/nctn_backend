package quanphung.hust.nctnbackend.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequestMapping(SseEmitterOperations.API_RESOURCE)
public interface SseEmitterOperations {
    String MEMBER_ID_HEADER = "MemberId";
    String API_RESOURCE = "/api/sse";
    String SUBSCRIBE="/subscribe";

    @CrossOrigin
    @GetMapping(value = SUBSCRIBE)
    SseEmitter subscribeToEvents();


}
