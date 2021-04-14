package com.codetogether.openstudio.scheduler;

import com.codetogether.openstudio.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduleTask {

    private final MailService mailService;

    private int num = 1;

    /**
     * cron = 초 분 시 일 월 요일 (옵셔널)년
     */
    @Scheduled(cron = "10 * * * * *")
    public void testScheduleFunc() {
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
        System.out.println("scheduledTask = " + String.valueOf(num++));
//        mailService.sendMail("jeon4708@gmail.com");
    }

    /**
     * 주마다 새로운 풀 생성
     */
    @Scheduled(cron = "42 42 16 * * 1")
    public void createWeeklyPools() {
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
        System.out.println("scheduledTask = " + String.valueOf(num++));
//        mailService.sendMail("jeon4708@gmail.com");
    }

    /**
     * 예약으로부터 팀 배정
     */
    @Scheduled(cron = "0 0 16 * * 1")
    public void matchTeamFromReservation() {
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
        System.out.println("scheduledTask = " + String.valueOf(num++));
//        mailService.sendMail("jeon4708@gmail.com");
    }
}