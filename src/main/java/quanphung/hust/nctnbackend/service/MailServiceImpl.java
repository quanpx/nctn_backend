package quanphung.hust.nctnbackend.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import quanphung.hust.nctnbackend.dto.email.EmailDetails;

@Service
@Slf4j
public class MailServiceImpl implements MailService
{
  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String sender;

  @Autowired
  private SpringTemplateEngine springTemplateEngine;

  @Autowired
  private FreeMarkerConfigurer freeMarkerConfigurer;

  @Override
  public String sendSimpleMail(EmailDetails details)
  {

    // Try block to check for exceptions
    try
    {

      // Creating a simple mail message
      SimpleMailMessage mailMessage
        = new SimpleMailMessage();

      // Setting up necessary details
      mailMessage.setFrom(sender);
      mailMessage.setTo(details.getRecipient());
      mailMessage.setText(details.getMsgBody());
      mailMessage.setSubject(details.getSubject());

      // Sending the mail
      javaMailSender.send(mailMessage);
      return "Mail Sent Successfully...";
    }

    // Catch block to handle the exceptions
    catch (Exception e)
    {
      log.error(e.getMessage());
      return "Error while Sending Mail";
    }
  }

  @Override
  public String sendMailWithAttachment(EmailDetails details)
  {
    // Creating a mime message
    MimeMessage mimeMessage
      = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper;

    try
    {

      // Setting multipart as true for attachments to
      // be send
      mimeMessageHelper
        = new MimeMessageHelper(mimeMessage, true);
      mimeMessageHelper.setFrom(sender);
      mimeMessageHelper.setTo(details.getRecipient());
      mimeMessageHelper.setText(details.getMsgBody());
      mimeMessageHelper.setSubject(
        details.getSubject());

      // Adding the attachment
      FileSystemResource file
        = new FileSystemResource(
        new File(details.getAttachment()));

      mimeMessageHelper.addAttachment(
        file.getFilename(), file);

      // Sending the mail
      javaMailSender.send(mimeMessage);
      return "Mail sent Successfully";
    }

    // Catch block to handle MessagingException
    catch (MessagingException e)
    {

      // Display message when exception occurred
      return "Error while sending mail!!!";
    }
  }

  @Async
  @Override
  public void sendMailWithThymeleaf(EmailDetails details, String template)
  {
    log.info("Send email async with template");
    Context thymmeCtx = new Context();
    Map<String, Object> models = new HashMap<>();
    models.put("recipientName", details.getRecipient());
    models.put("receiver",details.getReceiver());
    models.put("auctionId", details.getAuctionId());
    models.put("auctionName", details.getAuctionName());
    models.put("auctionTime",details.getTime());
    models.put("wonLots",details.getWonLots());
    models.put("senderName", "auctionantique.app@gmail.com");
    thymmeCtx.setVariables(models);

    String htmlbody = springTemplateEngine.process(template, thymmeCtx);
    sendHtmlMessage(details.getRecipient(), details.getSubject(), htmlbody);
  }

  @Async
  @Override
  public void sendMailWithFreeMarker(EmailDetails details)
  {
    try
    {
      log.info("Send email async whit template");
      Map<String, Object> models = new HashMap<>();
      models.put("recipientName", details.getRecipient());
      models.put("text", details.getMsgBody());
      models.put("senderName", "quanpx.dev@mgail.com");

      Template template = freeMarkerConfigurer.getConfiguration().getTemplate("hello");
      String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template, models);
      sendHtmlMessage(details.getRecipient(), details.getSubject(), htmlBody);

    }
    catch (IOException | TemplateException e)
    {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private void sendHtmlMessage(String recipient, String subject, String htmlbody)
  {

    try
    {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

      helper.setFrom("reviewbook.app@gmail.com");
      helper.setTo(recipient);
      helper.setText(htmlbody, true);
      helper.setSubject(subject);
      log.info("Sending email to " + recipient);
      javaMailSender.send(message);
    }
    catch (MessagingException e)
    {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }

  }
}
