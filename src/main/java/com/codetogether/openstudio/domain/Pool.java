package com.codetogether.openstudio.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Pool {
    @Id
    @GeneratedValue
    @Column(name = "pool_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private LocalDateTime closedAt;

    @OneToMany(mappedBy = "pool", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    public Pool() {
    }

    public Pool(Subject subject, LocalDateTime createdAt, LocalDateTime closedAt) {
        this.subject = subject;
        this.closedAt = closedAt;
    }
}
