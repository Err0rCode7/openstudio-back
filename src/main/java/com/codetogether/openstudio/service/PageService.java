package com.codetogether.openstudio.service;

import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Reservation;
import com.codetogether.openstudio.dto.CommonResponseDto;
import com.codetogether.openstudio.dto.page.*;
import com.codetogether.openstudio.properties.CadetProperty;
import com.codetogether.openstudio.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PageService {

    private final ReservationRepository reservationRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final SubjectRepository subjectRepository;
    private final PoolRepository poolRepository;
    private final MemberRepository memberRepository;
    private final CadetProperty cadetProperty;

    @Transactional
    public Page1ResponseDto getPage1(String intraId) {
        UserReservation currentMatchingReservation;

        Optional<Reservation> reservation = reservationRepository.findByMemberNameAndDateBetween(intraId, LocalDateTime.now());
        if (reservation.isPresent()) {
            int userCount = reservationRepository.countByPoolId(reservation.get().getPool().getId());
            currentMatchingReservation = new UserReservation(reservation.get(), userCount);
        } else {
            currentMatchingReservation = null;
        }

        List<PastTeamLog> pastTeamLog = teamMemberRepository.findByMemberName(intraId).stream()
                .map((teamMember -> new PastTeamLog(teamMember.getTeam())))
                .collect(Collectors.toList());

        return new Page1ResponseDto(true, intraId, currentMatchingReservation, pastTeamLog);
    }

    @Transactional
    public Page2ResponseDto getPage2() {
        List<SubjectInfo> subjectInfos = subjectRepository.findAllOrderByCircleASC().stream()
                .map(subject -> new SubjectInfo(subject,
                        reservationRepository
                                .findBySubjectIdAndDateBetween(
                                        subject.getId(),
                                        LocalDateTime.now()).size()))
                .collect(Collectors.toList());
        return new Page2ResponseDto(subjectInfos);
    }

    @Transactional
    public CommonResponseDto saveReservation(Page2ReservationRequestDto requestDto) {
        List<Pool> pools = poolRepository.findBySubjectNameAndDateBetween(LocalDateTime.now(), requestDto.getSubjectName());
        if (pools.size() == 0) {
            throw new IllegalArgumentException(
                    "???????????? ?????? ???????????? ???????????????. subjectName = "+ requestDto.getSubjectName());
        } else {
            Member member = memberRepository.findByName(requestDto.getIntraId())
                    .orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ???????????? ?????????. memberName = " + requestDto.getIntraId()));
            reservationRepository.save(Reservation.builder()
                    .pool(pools.get(0))
                    .member(member)
                    .build());
            return new CommonResponseDto(true, "????????? ??????????????? ?????????????????????.");
        }
    }

    @Transactional
    public CommonResponseDto deleteReservationByUserName(String memberName) {
        Reservation reservation = reservationRepository.findByMemberNameAndDateBetween(memberName, LocalDateTime.now())
                .orElseThrow(() -> new IllegalArgumentException("????????? ?????? ????????? ????????? ????????? ????????????. memberName = " + memberName));
        reservationRepository.deleteById(reservation.getId());
        return new CommonResponseDto(true, "??????????????? ????????? ?????????????????????.");
    }

    @Transactional(readOnly = true)
    public Page3ResponseDto getPage3() {
        Integer cadetCount = cadetProperty.getCadetCount();
        Integer openStudioUserCount = (int) memberRepository.count();
        Integer totalOpenStudioTeamCount = (int) teamRepository.count();
        Integer totalMatchedUserCount = (int) teamMemberRepository.count();
        Integer totalReservationCountForThisWeek = (int) reservationRepository.countByDateBetween(LocalDateTime.now());

        return Page3ResponseDto.builder()
                .cadetCount(cadetCount)
                .openStudioUserCount(openStudioUserCount)
                .totalOpenStudioTeamCount(totalOpenStudioTeamCount)
                .totalMatchedUserCount(totalMatchedUserCount)
                .totalReservationCountForThisWeek(totalReservationCountForThisWeek)
                .build();
    }
}
