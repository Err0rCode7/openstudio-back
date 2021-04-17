package com.codetogether.openstudio.dto.page;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Page3ResponseDto {

    private Integer openStudioUserCount;
    private Integer cadetCount;
    private Integer totalOpenStudioTeamCount;
    private Integer totalMatchedUserCount;
    private Integer totalReservationCountForThisWeek;

    @Builder
    public Page3ResponseDto(Integer openStudioUserCount, Integer cadetCount, Integer totalOpenStudioTeamCount, Integer totalMatchedUserCount, Integer totalReservationCountForThisWeek) {
        this.openStudioUserCount = openStudioUserCount;
        this.cadetCount = cadetCount;
        this.totalOpenStudioTeamCount = totalOpenStudioTeamCount;
        this.totalMatchedUserCount = totalMatchedUserCount;
        this.totalReservationCountForThisWeek = totalReservationCountForThisWeek;
    }
}
