package quanphung.hust.nctnbackend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import quanphung.hust.nctnbackend.dto.response.NotiResponse;

@RequestMapping(NotiOperations.API_RESOURCE)
public interface NotiOperations
{
    String MEMBER_ID_HEADER = "MemberId";
    String API_RESOURCE = "/api/noti";



    @GetMapping
    ResponseEntity<NotiResponse> getNotis();


}
