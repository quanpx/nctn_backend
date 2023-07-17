package quanphung.hust.nctnbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import quanphung.hust.nctnbackend.dto.response.NotiResponse;
import quanphung.hust.nctnbackend.service.NotiService;
import quanphung.hust.nctnbackend.service.SseService;

@RestController
@Slf4j
public class NotiController implements NotiOperations
{


    @Autowired
    private NotiService notiService;



  @Override
  public ResponseEntity<NotiResponse> getNotis()
  {
    return ResponseEntity.ok(notiService.getNotis());
  }
}
