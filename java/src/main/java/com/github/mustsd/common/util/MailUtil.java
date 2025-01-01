package com.github.mustsd.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * @author yangz
 * @date 2022-04-01 15:38
 */
@Slf4j
@Component
public class MailUtil {

  @Value("${spring.application.name}")
  private String emailName;

  @Value("${spring.mail.username}")
  private String username;

  @Resource JavaMailSender mailSender;

  public void sendSimpleMail(String email, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(username);
    message.setTo(email);
    message.setSubject(subject);
    message.setText(text);
    mailSender.send(message);
  }

  public void sendAttachmentMail(String to, String subject, String html, String filePath)
      throws MessagingException, UnsupportedEncodingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    // 设置utf-8或GBK编码，否则邮件会有乱码
    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    messageHelper.setFrom(username, emailName);
    messageHelper.setTo(to);
    messageHelper.setSubject(subject);
    messageHelper.setText(html, true);
    FileSystemResource file = new FileSystemResource(new File(filePath));
    String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
    messageHelper.addAttachment(fileName, file);
    mailSender.send(mimeMessage);
  }

  public void sendSimpleMailHtml(String email, String subject, String html)
      throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setFrom(username);
    helper.setTo(email);
    helper.setSubject(subject);
    helper.setText(html, true);
    // 发送邮件
    mailSender.send(mimeMessage);
  }
}
