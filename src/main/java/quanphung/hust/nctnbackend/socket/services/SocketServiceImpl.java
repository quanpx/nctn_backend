package quanphung.hust.nctnbackend.socket.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import quanphung.hust.nctnbackend.socket.controller.MessageController;
import quanphung.hust.nctnbackend.socket.message.Message;

@Service
@Slf4j
public class SocketServiceImpl implements SocketService
{
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @Autowired
  private SimpUserRegistry simpUserRegistry;

  @Override
  public void sendToAll(Message message, String dest, String user)
  {
    List<String> subscribers;

      if(StringUtils.hasText(user))
      {
        subscribers = simpUserRegistry.getUsers().stream()
        .map(SimpUser::getName)
        .filter(name -> !user.equals(name))
        .collect(Collectors.toList());
    }else
    {
      subscribers = simpUserRegistry.getUsers().stream()
        .map(SimpUser::getName)
        .collect(Collectors.toList());
    }

    for (String sub:subscribers)
    {
      log.info("Send to: " + sub + " - Type: " + message.getType());
      simpMessagingTemplate.convertAndSendToUser(sub, dest, message);
    }

  }

  @Override
  public void sendToUser(Message message, String user, String dest)
  {
    simpMessagingTemplate.convertAndSendToUser(user, dest, message);
  }
}
