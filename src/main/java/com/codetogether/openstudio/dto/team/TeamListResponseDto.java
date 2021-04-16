package com.codetogether.openstudio.dto.team;

import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Team;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TeamListResponseDto {
    private Long id;
    private Pool pool;
    private List<String> userNames;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    public TeamListResponseDto(Team entity) {
        this.id = entity.getId();
        this.pool = entity.getPool();
        this.createdAt = entity.getCreatedAt();
        this.closedAt = entity.getClosedAt();
        this.userNames = entity.getTeamMembers().stream()
                .map(teamMember -> teamMember.getMember().getName())
                .collect(Collectors.toList());
    }

    public TeamListResponseDto(Team entity, List<String> userNames) {
        this.id = entity.getId();
        this.pool = entity.getPool();
        this.createdAt = entity.getCreatedAt();
        this.closedAt = entity.getClosedAt();
        this.userNames = userNames;
    }
}
