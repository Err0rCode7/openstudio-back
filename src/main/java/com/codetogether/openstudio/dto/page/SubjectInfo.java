package com.codetogether.openstudio.dto.page;

import com.codetogether.openstudio.domain.Subject;
import lombok.Getter;

@Getter
public class SubjectInfo {
    private String name;
    private String description;
    private Long circleNumber;
    private String pdfReference;
    private Integer userCountForReservation;

    public SubjectInfo(Subject entity, int userCount) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.circleNumber = entity.getCircle();
        this.pdfReference = entity.getPdfRef();
        this.userCountForReservation = userCount;
    }
}
