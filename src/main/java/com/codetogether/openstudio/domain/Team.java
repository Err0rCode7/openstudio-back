package com.codetogether.openstudio.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
@Entity
public class Team extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pool_id")
    private Pool pool;
    private LocalDateTime closedAt;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> teamMembers = new ArrayList<>();

    public Team(Pool pool, LocalDateTime closedAt, List<Member> members) {
        this.pool = pool;
        this.closedAt = closedAt;
        this.teamMembers = members.stream()
                .map(member -> new TeamMember(member, this))
                .collect(Collectors.toList());
    }

    public void update(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
