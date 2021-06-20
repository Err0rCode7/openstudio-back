package com.codetogether.openstudio.service;

import com.codetogether.openstudio.domain.Team;
import com.codetogether.openstudio.properties.CadetProperty;
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
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final CadetProperty cadetProperty;
    public void sendSurveyFormTo(String email) {
        String survey = cadetProperty.getSurveyForm();

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


    public void sendSurveyForm(List<String> emails) {
        for (String email : emails) {
            sendSurveyFormTo(email);
        }
    }

    public void sendSurveyForm(Team team) {
        List<String> emails = team.getTeamMembers().stream()
                .map(teamMember -> teamMember.getMember().getEmail())
                .collect(Collectors.toList());
        sendSurveyForm(emails);
    }
}
