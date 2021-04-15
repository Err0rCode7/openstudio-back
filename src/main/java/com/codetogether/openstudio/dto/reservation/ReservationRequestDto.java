package com.codetogether.openstudio.dto.reservation;

import com.codetogether.openstudio.domain.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Getter
public class ReservationRequestDto {

    private String subjectName;
    private String userName;
}
