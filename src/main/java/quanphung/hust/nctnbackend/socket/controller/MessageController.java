package quanphung.hust.nctnbackend.socket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import quanphung.hust.nctnbackend.socket.message.Greeting;
import quanphung.hust.nctnbackend.socket.message.HelloMessage;
import quanphung.hust.nctnbackend.socket.message.Message;
import quanphung.hust.nctnbackend.socket.services.SocketService;

@Controller
public class MessageController
{
  @Autowired
  private SocketService socketService;

  @Autowired
  private SimpUserRegistry simpUserRegistry;

  @MessageMapping("/hello")
  public void send(SimpMessageHeaderAccessor sha) {
    String user = sha.getUser().getName();
    Message message = Message.builder().message("Hello from " + user).build();
    socketService.sendToUser(message,user,"/queue/messages");

  }
  @MessageMapping("/auction")
  @SendTo("/topic/auction")
  public void greeting(SimpMessageHeaderAccessor sha) throws Exception {
    Thread.sleep(1000); // simulated delay
    Message message = Message.builder().message("Hello, "+simpUserRegistry.getUserCount()+" people").build();
    socketService.sendToAll(message,"/topic/auction");
  }
}
