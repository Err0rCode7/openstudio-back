package com.codetogether.openstudio.dto.pool;

import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Subject;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PoolResponseDto {
    private Subject subject;
    private LocalDateTime closedAt;

    public PoolResponseDto(Pool entity) {
        this.subject = entity.getSubject();
        this.closedAt = entity.getClosedAt();
    }
}

