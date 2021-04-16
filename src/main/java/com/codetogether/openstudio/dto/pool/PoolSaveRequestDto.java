package com.codetogether.openstudio.dto.pool;

import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Subject;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PoolSaveRequestDto {
    private Subject subject;
    private LocalDateTime closedAt;

    public Pool toEntity() {
        return Pool.builder()
                .subject(subject)
                .closedAt(closedAt)
                .build();
    }
}
