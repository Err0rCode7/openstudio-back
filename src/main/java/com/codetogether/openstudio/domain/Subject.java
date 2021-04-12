package com.codetogether.openstudio.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Subject {
    @Id
    @GeneratedValue
    @Column(name = "subject_id")
    private Long id;
    private String name;
    private String pdfRef;
    private String description;
    private Long circle;

    public Subject() {
    }

    public Subject(String name, String pdfRef, String desc, Long circle) {
        this.name = name;
        this.pdfRef = pdfRef;
        this.description = desc;
        this.circle = circle;
    }
//
//    public Subject(NewSubjectDto newSubjectDto) {
//        this.name = newSubjectDto.getName();
//        this.pdfRef = newSubjectDto.getPdfRef();
//        this.description = newSubjectDto.getDesc();
//        this.circle = newSubjectDto.getCircle();
//    }
}
