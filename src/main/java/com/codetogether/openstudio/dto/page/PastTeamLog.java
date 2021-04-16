package com.codetogether.openstudio.dto.page;

import com.codetogether.openstudio.domain.Team;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PastTeamLog {
    private String subjectName;
    private List<String> members;
    private LocalDateTime matchedDate;

    public PastTeamLog(String subjectName, List<String> members, LocalDateTime matchedDate) {
        this.subjectName = subjectName;
        this.members = members;
        this.matchedDate = matchedDate;
    }

    public PastTeamLog(Team entity) {
        this.subjectName = entity.getPool().getSubject().getName();
        this.matchedDate = entity.getCreatedAt();
        this.members = entity.getTeamMembers().stream()
                .map((teamMember -> teamMember.getMember().getName()))
                .collect(Collectors.toList());
    }
}
