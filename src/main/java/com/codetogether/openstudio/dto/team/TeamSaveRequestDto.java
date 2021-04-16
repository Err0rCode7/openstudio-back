package com.codetogether.openstudio.dto.team;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class TeamSaveRequestDto {
    private String subjectName;
    private List<String> userNames = new ArrayList<>();
}
