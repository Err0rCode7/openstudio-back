package com.codetogether.openstudio.service;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Reservation;
import com.codetogether.openstudio.repository.MemberRepository;
import com.codetogether.openstudio.repository.PoolRepository;
import com.codetogether.openstudio.repository.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Autowired
    InitService initService;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PoolRepository poolRepository;

    @Autowired
    TeamService teamService;

    @Test
    @DisplayName("팀 배정을 하고 메일서비스에 매칭된 유저들의 메일이있는 지 확인")
    public void 메일리스트확인() {
        //given
        // 3, 4명 있는 팀으로 팀 배정이 완료가 되었다.
        this.initService.initSubjectTable();
        this.initService.createWeeklyPools();

        //7명의 멤버가 libft를 예약
        Member member1 = memberRepository.save(new Member("member1", "1", "1", Role.USER));
        Member member2 = memberRepository.save(new Member("member2", "2", "2", Role.USER));
        Member member3 = memberRepository.save(new Member("member3", "3", "3", Role.USER));
        Member member4 = memberRepository.save(new Member("member4", "4", "4", Role.USER));
        Member member5 = memberRepository.save(new Member("member5", "5", "5", Role.USER));
        Member member6 = memberRepository.save(new Member("member6", "6", "6", Role.USER));
        Member member7 = memberRepository.save(new Member("member7", "7", "7", Role.USER));

        List<Pool> pools = poolRepository.findBySubjectNameAndDateBetween(LocalDateTime.now(), "libft");
        reservationRepository.save(new Reservation(member1, pools.get(0)));
        reservationRepository.save(new Reservation(member2, pools.get(0)));
        reservationRepository.save(new Reservation(member3, pools.get(0)));
        reservationRepository.save(new Reservation(member4, pools.get(0)));
        reservationRepository.save(new Reservation(member5, pools.get(0)));
        reservationRepository.save(new Reservation(member6, pools.get(0)));
        reservationRepository.save(new Reservation(member7, pools.get(0)));

        //3, 4명으로 팀이 만들어진다.
        teamService.matchAllReservationsOfPools();

        //when
        //메일 서비스의 메일리스트를 확인했을 때
        List<String> mailList = mailService.getCopiedTargets();

        //then
        //총 7명의 메일이 있어야 한다.
        assertThat(mailList.size()).isEqualTo(7);
        for (String mail : mailList) {
            System.out.println("mail = " + mail);
        }
    }
}