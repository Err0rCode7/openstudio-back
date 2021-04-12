package com.codetogether.openstudio.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private LocalDateTime closedAt;

    public Team(Subject subject, LocalDateTime createdAt, LocalDateTime closedAt) {
        this.subject = subject;
        this.closedAt = closedAt;
    }
}
