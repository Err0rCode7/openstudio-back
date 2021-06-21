package com.codetogether.openstudio.util;

import com.codetogether.openstudio.domain.*;
import com.codetogether.openstudio.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DividerUtils {
    private final int DEFAULT_DIVISION_SIZE = 3;
    private final Collector<Reservation, ?, Stream<Reservation>> SHUFFLER = CollectorUtils.toShuffledStream();
    private int index = 0;

    public <T> int getNextOffset(List<T> reservations) {
        if ((reservations.size() - index) % DEFAULT_DIVISION_SIZE == 0) {
            return 3;
        } else {
            if (reservations.size() - index > DEFAULT_DIVISION_SIZE) {
                return DEFAULT_DIVISION_SIZE + 1;
            } else {
                return 0;
            }
        }
    }

    public Optional<List<Reservation>> getNextReservations(List<Reservation> reservations) {
        int nextOffset = getNextOffset(reservations);
        if (nextOffset == 0) {
            index = 0;
            return Optional.empty();
        } else {
            index += nextOffset;
            return Optional.of(reservations.subList(index - nextOffset, index));
        }
    }

    public List<List<Reservation>> divideReservations(List<Reservation> reservations, Subject subject) {
        List<List<Reservation>> dividedReservations = new ArrayList<>();
        while (index + DEFAULT_DIVISION_SIZE <= reservations.size()) {
            Optional<List<Reservation>> nextReservations = getNextReservations(reservations);
            if (!nextReservations.isPresent()) {
                break;
            }
            dividedReservations.add(nextReservations.get());
        }
        return dividedReservations;
    }

    public List<Team> getTeamList(Pool pool) {
        List<Reservation> randomReservations = pool.getReservations().stream()
                .collect(SHUFFLER)
                .collect(Collectors.toList());
        return divideReservations(randomReservations, pool.getSubject()).stream()
                .map(reservations -> {
                    List<Member> members = reservations.stream()
                            .map(reservation -> {
                                reservation.close();
                                Member member = reservation.getMember();
                                return member;
                            })
                            .collect(Collectors.toList());
                    return new Team(pool, LocalDateTime.now().plusDays(7), members);
                })
                .collect(Collectors.toList());
    }
}
