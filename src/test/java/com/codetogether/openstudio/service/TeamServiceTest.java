package com.codetogether.openstudio.service;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.*;
import com.codetogether.openstudio.dto.reservation.ReservationListResponseDto;
import com.codetogether.openstudio.dto.reservation.ReservationRequestDto;
import com.codetogether.openstudio.dto.team.TeamListResponseDto;
import com.codetogether.openstudio.dto.team.TeamSaveRequestDto;
import com.codetogether.openstudio.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TeamServiceTest {

    @Autowired
    TeamService teamService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    PoolRepository poolRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    @Transactional
    @DisplayName("Team 정상 페이지별 조회 test")
    public void 정상_페이지별_조회_테스트() {

        // given
        // 팀이 N개가 있는 상황에서
        // 컨트롤러로부터 페이지 요청을 전달 받았다.
        // 페이지는 사이즈가 10이고 두 번째 페이지 요청이다.

        teamRepository.deleteAll();

        int N = 12;
        int pageSize = 10;
        int requestedPageNumber = 2 - 1;

        List<Member> members = new ArrayList<>();

        for (int i = 0; i < 3 * N; i++) {
            String number = Integer.toString(i);
            members.add(memberRepository.save(new Member("member" + number, number, number, Role.USER)));
        }

        Subject subject = subjectRepository.save(new Subject("subjectName", "subjectTest", "subjectTest", 1L));
        Pool pool = poolRepository.save(new Pool(subject, LocalDateTime.now().plusDays(1)));

        List<List<Member>> listOfMembers = new ArrayList<>();

        for (int i = 0; i < 3 * N; i += 3) {
            listOfMembers.add(members.subList(i, i + 3));
        }

        listOfMembers.stream()
                .map((memberList ->
                    teamRepository.save(
                        new Team(pool, LocalDateTime.now().plusDays(1), memberList))))
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(requestedPageNumber, pageSize);

        // when
        // 내림차순으로 페이지를 조회하는 서비스를 통해 조회를하면
        Page<TeamListResponseDto> responseDtos = teamService.findAllDesc(pageable);

        // 12개의 예약 중 두개의 첫 번째와 두 번째가 조회되어야한다.

        assertThat(responseDtos.getNumberOfElements()).isEqualTo(2);
        List<TeamListResponseDto> content = responseDtos.getContent();
        int i = N - pageSize * requestedPageNumber;

        assertThat(responseDtos.getPageable().getPageNumber()).isEqualTo(1);
        assertThat(responseDtos.getPageable().getOffset()).isEqualTo(10);
    }

    @Test
    @Transactional
    @DisplayName("Reservation 정상 save test")
    public void 정상_세이브테스트() {

        // given
        // 세이브 요청 dto를 컨트롤러를 통해 전달받았다.

        List<Member> members = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String number = Integer.toString(i);
            members.add(memberRepository.save(new Member("member" + number, number, number, Role.USER)));
        }

        Subject subject = subjectRepository.save(new Subject("subjectName", "subjectTest", "subjectTest", 1L));
        Pool pool = poolRepository.save(new Pool(subject, LocalDateTime.now().plusDays(1)));

        List<String> names = members.stream()
                .map((member -> new String(member.getName())))
                .collect(Collectors.toList());
        TeamSaveRequestDto requestDto = new TeamSaveRequestDto(subject.getName(), names);

        // when
        // dto로 팀 세이브 서비스를 진행할때
        Long savedTeamId = teamService.save(requestDto);

        // Id로 멤버 조회시 해당 멤버가 있어야한다.

        Optional<Team> result = teamRepository.findById(savedTeamId);

        assertThat(result.isPresent()).isTrue();

        assertThat(result.get().getPool().getSubject().getName()).isEqualTo(subject.getName());
        assertThat(result.get().getTeamMembers().size()).isEqualTo(3);
    }


    @Test
    @Transactional
    @DisplayName("Team 정상 delete test")
    public void 정상_삭제테스트() {

        // given
        // 삭제할 팀이 존재하고
        // 컨트롤러를 통해 팀 삭제 요청으로 id를 전달 받았다.
        List<Member> members = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String number = Integer.toString(i);
            members.add(memberRepository.save(new Member("member" + number, number, number, Role.USER)));
        }

        Subject subject = subjectRepository.save(new Subject("subjectName", "subjectTest", "subjectTest", 1L));
        Pool pool = poolRepository.save(new Pool(subject, LocalDateTime.now().plusDays(1)));

        Team team = teamRepository.save(new Team(pool, LocalDateTime.now().plusDays(7), members));

        // when
        // 예약 서비스가 해당 아이디의 예약을 삭제를 하면
        teamService.delete(team.getId());

        // Id로 조회가 되지 않아야한다.

        Optional<Team> result = teamRepository.findById(team.getId());

        assertThat(result.isEmpty()).isTrue();
    }
}