package com.codetogether.openstudio.service;

import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Reservation;
import com.codetogether.openstudio.dto.reservation.ReservationListResponseDto;
import com.codetogether.openstudio.dto.reservation.ReservationRequestDto;
import com.codetogether.openstudio.dto.reservation.ReservationResponseDto;
import com.codetogether.openstudio.repository.MemberRepository;
import com.codetogether.openstudio.repository.PoolRepository;
import com.codetogether.openstudio.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final PoolRepository poolRepository;

    public List<ReservationListResponseDto> findAllDesc() {
        return reservationRepository.findAllDesc().stream()
                .map(ReservationListResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ReservationListResponseDto> findByDateBetween(LocalDateTime date) {
        return reservationRepository.findByDateBetween(date).stream()
                .map(ReservationListResponseDto::new)
                .collect(Collectors.toList());
    }

    public Long save(ReservationRequestDto requestDto) {
        List<Pool> pools = poolRepository.findBySubjectNameAndDateBetween(LocalDateTime.now(), requestDto.getSubjectName());
        if (pools.size() == 0) {
            throw new IllegalArgumentException(
                    "유효하지 않은 서브젝트 이름입니다. subjectName = "+ requestDto.getSubjectName());
        } else {
            Member member = memberRepository.findByName(requestDto.getUserName())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 유저이름 입니다. memberName = " + requestDto.getUserName()));
            return reservationRepository.save(Reservation.builder()
                                    .pool(pools.get(0))
                                    .member(member)
                                    .build())
                                        .getId();
        }
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }
}
