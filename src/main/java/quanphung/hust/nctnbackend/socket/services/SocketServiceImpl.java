package quanphung.hust.nctnbackend.socket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import quanphung.hust.nctnbackend.socket.message.Message;

@Service
public class SocketServiceImpl implements SocketService
{
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @Override
  public void sendToAll(Message message,String dest)
  {
      simpMessagingTemplate.convertAndSend(dest,message);
  }

  @Override
  public void sendToUser(Message message, String user, String dest)
  {
      simpMessagingTemplate.convertAndSendToUser(user,dest,message);
  }
}
