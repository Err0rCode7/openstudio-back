package com.codetogether.openstudio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;

    public void sendMail(String email) {
        MimeMessage mail = mailSender.createMimeMessage();
        String htmlStr = "<h2>Hello! This is OpenStudio Email </h2><br><br>";
        try {
            mail.setSubject("[결과발송] OpenStudio 결과메일입니다", "utf-8");
            mail.setText(htmlStr, "utf-8", "html");
            mail.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            mailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
