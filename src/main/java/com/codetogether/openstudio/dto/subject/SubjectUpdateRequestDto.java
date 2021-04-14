package com.codetogether.openstudio.dto.subject;

import com.codetogether.openstudio.domain.Subject;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SubjectUpdateRequestDto {
    private Long id;
    private String name;
    private String pdfRef;
    private String description;
    private Long circle;

    @Builder
    public SubjectUpdateRequestDto(Long id, String name, String pdfRef, String description, Long circle) {
        this.id = id;
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
