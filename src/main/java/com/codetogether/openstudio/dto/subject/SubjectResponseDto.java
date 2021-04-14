package com.codetogether.openstudio.dto.subject;

import com.codetogether.openstudio.domain.Subject;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SubjectResponseDto {
    private Long id;
    private String name;
    private String pdfRef;
    private String description;
    private Long circle;
    private LocalDateTime modifiedAt;

    public SubjectResponseDto(Subject entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.pdfRef = entity.getPdfRef();
        this.description = entity.getDescription();
        this.circle = entity.getCircle();
        this.modifiedAt = entity.getModifiedAt();
    }
}
