package quanphung.hust.nctnbackend.service;

import quanphung.hust.nctnbackend.dto.email.EmailDetails;

public interface MailService
{
  // Method
  // To send a simple email
  String sendSimpleMail(EmailDetails details);

  // Method
  // To send an email with attachment
  String sendMailWithAttachment(EmailDetails details);

  void sendMailWithThymeleaf(EmailDetails details,String template);

  void sendMailWithFreeMarker(EmailDetails details);
}
