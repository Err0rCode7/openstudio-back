package com.codetogether.openstudio.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class TeamMember extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "team_member_id")
    private Long teamMemberId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public TeamMember(Member member, Team team) {
        this.member = member;
        this.team = team;
    }
}
