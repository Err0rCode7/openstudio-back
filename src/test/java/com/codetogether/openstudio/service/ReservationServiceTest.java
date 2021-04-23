package com.codetogether.openstudio.service;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Reservation;
import com.codetogether.openstudio.domain.Subject;
import com.codetogether.openstudio.dto.reservation.ReservationListResponseDto;
import com.codetogether.openstudio.dto.reservation.ReservationRequestDto;
import com.codetogether.openstudio.dto.subject.SubjectListResponseDto;
import com.codetogether.openstudio.dto.subject.SubjectSaveRequestDto;
import com.codetogether.openstudio.dto.subject.SubjectUpdateRequestDto;
import com.codetogether.openstudio.repository.MemberRepository;
import com.codetogether.openstudio.repository.PoolRepository;
import com.codetogether.openstudio.repository.ReservationRepository;
import com.codetogether.openstudio.repository.SubjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    PoolRepository poolRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Test
    @Transactional
    @DisplayName("Reservation 정상 페이지별 조회 test")
    public void 정상_페이지별_조회_테스트() {

        // given
        // 예약이 N개가 있는 상황에서
        // 컨트롤러로부터 페이지 요청을 전달 받았다.
        // 페이지는 사이즈가 10이고 두 번째 페이지 요청이다.

        reservationRepository.deleteAll();

        int N = 12;
        int pageSize = 10;
        int requestedPageNumber = 2 - 1;

        Member member1 = memberRepository.save(new Member("member1", "1", "1", Role.USER));
        Member member2 = memberRepository.save(new Member("member2", "2", "2", Role.USER));
        Member member3 = memberRepository.save(new Member("member3", "3", "3", Role.USER));
        Member member4 = memberRepository.save(new Member("member4", "4", "4", Role.USER));
        Member member5 = memberRepository.save(new Member("member5", "5", "5", Role.USER));
        Member member6 = memberRepository.save(new Member("member6", "6", "6", Role.USER));
        Member member7 = memberRepository.save(new Member("member7", "7", "7", Role.USER));
        Member member8 = memberRepository.save(new Member("member8", "8", "8", Role.USER));
        Member member9 = memberRepository.save(new Member("member9", "9", "9", Role.USER));
        Member member10 = memberRepository.save(new Member("member10", "10", "10", Role.USER));
        Member member11 = memberRepository.save(new Member("member11", "11", "11", Role.USER));
        Member member12 = memberRepository.save(new Member("member12", "12", "12", Role.USER));

        Subject subject = subjectRepository.save(new Subject("subjectName", "subjectTest", "subjectTest", 1L));
        Pool pool = poolRepository.save(new Pool(subject, LocalDateTime.now().plusDays(1)));

        reservationRepository.save(new Reservation(member1, pool));
        reservationRepository.save(new Reservation(member2, pool));
        reservationRepository.save(new Reservation(member3, pool));
        reservationRepository.save(new Reservation(member4, pool));
        reservationRepository.save(new Reservation(member5, pool));
        reservationRepository.save(new Reservation(member6, pool));
        reservationRepository.save(new Reservation(member7, pool));
        reservationRepository.save(new Reservation(member8, pool));
        reservationRepository.save(new Reservation(member9, pool));
        reservationRepository.save(new Reservation(member10, pool));
        reservationRepository.save(new Reservation(member11, pool));
        reservationRepository.save(new Reservation(member12, pool));

        Pageable pageable = PageRequest.of(requestedPageNumber, pageSize);

        // when
        // 내림차순으로 페이지를 조회하는 서비스를 통해 조회를하면
        Page<ReservationListResponseDto> responseDtos = reservationService.findAllDesc(pageable);

        // 12개의 예약 중 두개의 첫 번째와 두 번째가 조회되어야한다.

        assertThat(responseDtos.getNumberOfElements()).isEqualTo(2);
        List<ReservationListResponseDto> content = responseDtos.getContent();
        int i = N - pageSize * requestedPageNumber;

        for (ReservationListResponseDto reservationListResponseDto : content) {
            assertThat(reservationListResponseDto.getMember().getEmail()).isEqualTo(Integer.toString(i--));
        }

        assertThat(responseDtos.getPageable().getPageNumber()).isEqualTo(1);
        assertThat(responseDtos.getPageable().getOffset()).isEqualTo(10);
    }

    @Test
    @Transactional
    @DisplayName("Reservation 정상 save test")
    public void 정상_세이브테스트() {

        // given
        // dto를 컨트롤러를 통해 전달받았다.

        Member member1 = memberRepository.save(new Member("member12", "12", "12", Role.USER));
        Subject subject = subjectRepository.save(new Subject("subjectName", "subjectTest", "subjectTest", 1L));
        Pool pool = poolRepository.save(new Pool(subject, LocalDateTime.now().plusDays(1)));

        ReservationRequestDto requestDto = new ReservationRequestDto(subject.getName(), member1.getName());

        // when
        // dto로 예약 세이브 서비스를 진행할때
        Long savedReservationId = reservationService.save(requestDto);

        // Id로 멤버 조회시 해당 멤버가 있어야한다.

        Optional<Reservation> result = reservationRepository.findById(savedReservationId);

        assertThat(result.isPresent()).isTrue();

        assertThat(result.get().getMember().getName()).isEqualTo(member1.getName());
        assertThat(result.get().getMember().getEmail()).isEqualTo(member1.getEmail());
        assertThat(result.get().getPool().getSubject().getName()).isEqualTo(subject.getName());
    }

    @Test
    @Transactional
    @DisplayName("Reservation 정상 delete test")
    public void 정상_삭제테스트() {

        // given
        // 삭제할 예약이 존재하고
        // 컨트롤러를 통해 예약삭제를 요청으로 id를 전달 받았다.
        Member member1 = memberRepository.save(new Member("member12", "12", "12", Role.USER));
        Subject subject = subjectRepository.save(new Subject("subjectName", "subjectTest", "subjectTest", 1L));
        Pool pool = poolRepository.save(new Pool(subject, LocalDateTime.now().plusDays(1)));

        Reservation savedReservation = reservationRepository.save(new Reservation(member1, pool));

        // when
        // 예약 서비스가 해당 아이디의 예약을 삭제를 하면
        reservationService.delete(savedReservation.getId());

        // Id로 조회가 되지 않아야한다.

        Optional<Reservation> result = reservationRepository.findById(savedReservation.getId());

        assertThat(result.isEmpty()).isTrue();
    }
}