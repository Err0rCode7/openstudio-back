package com.codetogether.openstudio.dto.pool;

import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Subject;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PoolListResponseDto {
    private Long id;
    private Subject subject;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    public PoolListResponseDto(Pool entity) {
        this.id = entity.getId();
        this.subject = entity.getSubject();
        this.createdAt = entity.getCreatedAt();
        this.closedAt = entity.getClosedAt();
    }
}
