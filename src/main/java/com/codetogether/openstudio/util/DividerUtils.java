package com.codetogether.openstudio.util;

import com.codetogether.openstudio.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DividerUtils {

    private static final int DEFAULT_DIVISION_SIZE = 3;
    private static final Collector<Reservation, ?, Stream<Reservation>> SHUFFLER = CollectorUtils.toShuffledStream();
    private static int index = 0;

    public static <T> int getNextOffset(List<T> reservations) {
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

    public static Optional<List<Reservation>> getNextReservations(List<Reservation> reservations) {
        int nextOffset = getNextOffset(reservations);
        if (nextOffset == 0) {
            index = 0;
            return Optional.empty();
        } else {
            System.out.println("index = " + index);
            index += nextOffset;
            return Optional.of(reservations.subList(index - nextOffset, index));
        }
    }

    public static List<List<Reservation>> divideReservations(List<Reservation> reservations, Subject subject) {
        List<List<Reservation>> dividedReservations = new ArrayList<>();
        while (index + DEFAULT_DIVISION_SIZE <= reservations.size()) {
            Optional<List<Reservation>> nextReservations = getNextReservations(reservations);
            if (nextReservations.isPresent()) {
                dividedReservations.add(nextReservations.get());
            } else {
                break;
            }
        }
        return dividedReservations;
    }

    public static List<Team> getTeamList(Pool pool) {
        List<Reservation> randomReservations = pool.getReservations().stream()
                .collect(SHUFFLER)
                .collect(Collectors.toList());
        return divideReservations(randomReservations, pool.getSubject()).stream()
                .map(reservations -> {
                    List<Member> members = reservations.stream()
                            .map(reservation -> {
                                reservation.close();
                                return reservation.getMember();
                            })
                            .collect(Collectors.toList());
                    System.out.println("members.get(0) = " + members.get(0));
                    return new Team(pool, LocalDateTime.now().plusDays(7), members);
                })
                .collect(Collectors.toList());
    }
}
