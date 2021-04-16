package com.codetogether.openstudio.dto.page;

import lombok.Getter;

import java.util.List;

@Getter
public class Page1ResponseDto {
    private Boolean result;
    private String intraId;
    private UserReservation currentMatchingReservation;
    private List<PastTeamLog> pastTeamLog;

    public Page1ResponseDto(Boolean result, String intraId, UserReservation currentMatchingReservation, List<PastTeamLog> pastTeamLog) {
        this.result = result;
        this.intraId = intraId;
        this.currentMatchingReservation = currentMatchingReservation;
        this.pastTeamLog = pastTeamLog;
    }
}
