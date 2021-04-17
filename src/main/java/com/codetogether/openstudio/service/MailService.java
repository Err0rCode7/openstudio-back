package com.codetogether.openstudio.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final Environment environment;
    private final List<String> emails = new ArrayList<>();

    public void sendSurveyFormTo(String email) {
        String survey = environment.getProperty("openstudio.survey");

        MimeMessage mail = mailSender.createMimeMessage();
        String htmlStr = "<h2>Hello! This is OpenStudio Email </h2><br><br>" +
                "<a href=\"" + survey + "\"> 설문조사 참여 </a>" + "<br><br>";

        try {
            mail.setSubject("[결과발송] OpenStudio 결과메일입니다", "utf-8");
            mail.setText(htmlStr, "utf-8", "html");
            mail.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            mailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void addTarget(String email) {
        emails.add(email);
    }

    public void clearEmail() {
        emails.clear();
    }

    public int getTargetsCount() {
        return emails.size();
    }

    public List<String> getCopiedTargets() {
        return emails.stream()
                .map(String::new)
                .collect(Collectors.toList());
    }

    public void sendSurveyFormToTargets() {
        for (String email : emails) {
            sendSurveyFormTo(email);
        }
        clearEmail();
    }
}
