package com.codetogether.openstudio.dto.page;

import com.codetogether.openstudio.domain.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserReservation {
    private String subjectName;
    private LocalDateTime closedAt;
    private Integer userCount;

    public UserReservation(String subjectName, LocalDateTime closedAt, Integer userCount) {
        this.subjectName = subjectName;
        this.closedAt = closedAt;
        this.userCount = userCount;
    }

    public UserReservation(Reservation entity, int userCount) {
        this.subjectName = entity.getPool().getSubject().getName();
        this.closedAt = entity.getPool().getClosedAt();
        this.userCount = userCount;
    }
}
