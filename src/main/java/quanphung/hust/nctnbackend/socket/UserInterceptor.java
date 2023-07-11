package quanphung.hust.nctnbackend.socket;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import lombok.extern.slf4j.Slf4j;
import quanphung.hust.nctnbackend.socket.entity.User;
import quanphung.hust.nctnbackend.socket.services.SocketService;

@Slf4j
public class UserInterceptor implements ChannelInterceptor
{
  @Autowired
  private SocketService socketService;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

      if (raw instanceof Map) {
        Object name = ((Map) raw).get("username");

        if (name instanceof ArrayList) {
          accessor.setUser(new User(((ArrayList<String>) name).get(0).toString()));
        }
      }

    }else if(StompCommand.DISCONNECT.equals(accessor.getCommand()))
    {
//      quanphung.hust.nctnbackend.socket.message.Message message1 = quanphung.hust.nctnbackend.socket.message.Message.builder()
//          .message("Client disconnected!").build();
//      socketService.sendToAll(message1,"/topic/auction");
    }
    return message;
  }
}