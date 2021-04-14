package com.codetogether.openstudio.dto.subject;

import com.codetogether.openstudio.domain.Subject;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SubjectSaveRequestDto {
    private String name;
    private String pdfRef;
    private String description;
    private Long circle;

    @Builder
    public SubjectSaveRequestDto(String name, String pdfRef, String description, Long circle) {
        this.name = name;
        this.pdfRef = pdfRef;
        this.description = description;
        this.circle = circle;
    }

    public Subject toEntity() {
        return Subject.builder()
                .name(name)
                .pdfRef(pdfRef)
                .desc(description)
                .circle(circle)
                .build();
    }
}
