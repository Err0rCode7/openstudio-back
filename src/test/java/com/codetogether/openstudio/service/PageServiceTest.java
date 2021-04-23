package com.codetogether.openstudio.service;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.contorller.TeamsControllerTest;
import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Reservation;
import com.codetogether.openstudio.domain.Team;
import com.codetogether.openstudio.dto.page.Page3ResponseDto;
import com.codetogether.openstudio.dto.team.TeamListResponseDto;
import com.codetogether.openstudio.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PageServiceTest {

    @Autowired
    PageService pageService;

    @Autowired
    InitService initService;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PoolRepository poolRepository;

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepository teamRepository;

    @Test
    @Transactional
    @DisplayName("page3 정상 작동 테스트")
    public void 페이지3_테스트() {
        //given
        //팀이 (3명, 4명)으로 2개 만들어진 상황

        poolRepository.deleteAll();
        subjectRepository.deleteAll();

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
        //페이지3를 테스트해서 DTO를 가져왔을 때
        Page3ResponseDto responseDto = pageService.getPage3();

        //then
        //openStudioUserCount : 7
        //cadetCount : 900
        //totalOpenStudioTeamCount : 2
        //totalMatchedUserCount : 7
        //totalReservationCountForThisWeek : 7

        assertThat(responseDto.getOpenStudioUserCount()).isEqualTo(7);
        assertThat(responseDto.getCadetCount()).isEqualTo(900);
        assertThat(responseDto.getTotalOpenStudioTeamCount()).isEqualTo(2);
        assertThat(responseDto.getTotalMatchedUserCount()).isEqualTo(7);
        assertThat(responseDto.getTotalReservationCountForThisWeek()).isEqualTo(7);
    }
}