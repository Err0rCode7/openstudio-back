package com.codetogether.openstudio.dto.pool;

import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Subject;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PoolListResponseDto {
    private Subject subject;
    private LocalDateTime closedAt;

    public PoolListResponseDto(Pool entity) {
        this.subject = entity.getSubject();
        this.closedAt = entity.getClosedAt();
    }
}
