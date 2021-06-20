package com.codetogether.openstudio.scheduler;

import com.codetogether.openstudio.service.InitService;
import com.codetogether.openstudio.service.MailService;
import com.codetogether.openstudio.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ScheduleTask {

    private final MailService mailService;
    private final TeamService teamService;
    private final InitService initService;

    /**
     * cron = "초 분 시 일 월 요일 년도(생략가능)"
     * 매주 월요일(1) 16시 42분 42초마다 새로운 풀 생성 스케줄
     */
    @Scheduled(cron = "42 42 16 * * 1")
    public void createWeeklyPools() {
        System.out.println("scheduledTask CreateWeeklyPools : " + LocalDateTime.now());
        initService.createWeeklyPools();
    }

    /**
     * 매주 월요일(1) 16시 0분 0초마다 예약으로부터 팀 배정 스케줄
     */
    @Scheduled(cron = "0 0 16 * * 1")
    public void matchTeamFromReservation() {
        System.out.println("scheduledTask MatchTeam : " + LocalDateTime.now());
        teamService.matchAllReservationsOfPools();
    }
}