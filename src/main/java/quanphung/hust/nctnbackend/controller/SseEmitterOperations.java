package quanphung.hust.nctnbackend.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequestMapping(SseEmitterOperations.API_RESOURCE)
public interface SseEmitterOperations {
    String API_RESOURCE = "/api/sse";
    String SUBSCRIBE="/subscribe";
    String DISPATCH="/dispatch";

    @CrossOrigin
    @GetMapping(value = SUBSCRIBE,consumes = MediaType.ALL_VALUE)
   SseEmitter subscribe();

    @PostMapping(value = DISPATCH)
    void dispatch();

}
