package com.codetogether.openstudio.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Pool extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "pool_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private LocalDateTime closedAt;

    public Pool() {
    }

    @Builder
    public Pool(Subject subject, LocalDateTime closedAt) {
        this.subject = subject;
        this.closedAt = closedAt;
    }
}
