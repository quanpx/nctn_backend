package quanphung.hust.nctnbackend.socket.services;

import quanphung.hust.nctnbackend.socket.message.Message;

public interface SocketService
{

  void sendToAll(Message message, String dest, String user);
  void sendToUser(Message message, String user, String dest);
}
