package com.codetogether.openstudio.dto.page;

import lombok.Getter;

import java.util.List;

@Getter
public class Page2ResponseDto {
    public List<SubjectInfo> subjectInformation;

    public Page2ResponseDto(List<SubjectInfo> subjectInformation) {
        this.subjectInformation = subjectInformation;
    }
}
