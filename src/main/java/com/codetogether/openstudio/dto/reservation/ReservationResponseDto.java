package com.codetogether.openstudio.dto.reservation;

import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ReservationResponseDto {

    private Long id;
    private Member member;
    private Pool pool;

    public ReservationResponseDto(Reservation entity) {
        this.id = entity.getId();
        this.member = entity.getMember();
        this.pool = entity.getPool();
    }
}
