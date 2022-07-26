package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.sse.BidMessage;

import java.util.Map;

public interface SseService {
    void dispatchEvent(BidMessage message);
}
