package com.codetogether.openstudio.contorller;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Reservation;
import com.codetogether.openstudio.dto.team.TeamListResponseDto;
import com.codetogether.openstudio.repository.*;
import com.codetogether.openstudio.service.InitService;
import com.codetogether.openstudio.service.MailService;
import com.codetogether.openstudio.service.TeamService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
public class TeamsControllerTest {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    PoolRepository poolRepository;

    @Autowired
    InitService initService;

    @MockBean
    MailService mailService;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @DisplayName("매칭 기능 테스트")
    public void 매칭기능테스트() {

        //given
        //libft subject가 존재
        //이번주 libft에 대한 Pool이 존재

        poolRepository.deleteAll();
        subjectRepository.deleteAll();
        reservationRepository.deleteAll();

        this.initService.initSubjectTable();
        this.initService.createWeeklyPools();

        //7명의 멤버가 libft를 예약했다.
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

        em.flush();
        em.clear();

//        when
//        매칭 시작을 했을때
        teamService.matchAllReservationsOfPools();

//        then
//        마감된 풀의 reservation이 모두 closed여야한다.
        List<Reservation> reservations = reservationRepository.findByDateBetween(LocalDateTime.now());
        for (Reservation reservation : reservations) {
//            Assertions.assertThat(reservation.getIsClosed()).isEqualTo(true);
        }

//        랜덤하게 3명, 4명 팀으로 나눠진 Team 과 TeamMember 쿼리가 진행되어야한다.
//        Team -> 2개
//        TeamMember -> 7개

        List<TeamListResponseDto> teams = teamService.findAllDesc();
        Assertions.assertThat(teams.size()).isEqualTo(2);

        int sum = 0;
        for (TeamListResponseDto team : teams) {
            int size = team.getUserNames().size();
            sum += size;
        }
        Assertions.assertThat(sum).isEqualTo(7);
    }
}