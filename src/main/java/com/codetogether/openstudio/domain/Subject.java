package com.codetogether.openstudio.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Subject extends BaseTimeEntity {
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

    @Builder
    public Subject(String name, String pdfRef, String desc, Long circle) {
        this.name = name;
        this.pdfRef = pdfRef;
        this.description = desc;
        this.circle = circle;
    }

    public void update(String name, String pdfRef, String desc, Long circle) {
        this.name = name;
        this.pdfRef = pdfRef;
        this.description = desc;
        this.circle = circle;
    }

    public static List<Subject> getSampleSubjects() {
        List<Subject> savedSubjects = new ArrayList<>();
        Subject subject1 = new Subject("libft", "libft-pdf", "Its libft", 1L);
        Subject subject2 = new Subject("ft_printf", "ft_printf-pdf", "Its ft_printf", 2L);
        Subject subject3 = new Subject("get_next_line", "get_next_line-pdf", "Its get_next_line", 2L);
        Subject subject4 = new Subject("netwhat", "netwhat-pdf", "Its netwhat", 2L);
        Subject subject5 = new Subject("ft_server", "ft_server-pdf", "Its ft_server", 3L);
        Subject subject6 = new Subject("cub3d", "cub3d-pdf", "Its cub3d", 3L);
        Subject subject7 = new Subject("minirt", "minirt-pdf", "Its minirt", 4L);
        Subject subject8 = new Subject("libasm", "libasm-pdf", "Its libasm", 4L);
        Subject subject9 = new Subject("ft_service", "ft_service-pdf", "Its ft_service", 4L);
        Subject subject10 = new Subject("push_swap", "push_swap-pdf", "Its push_swap", 4L);
        Subject subject11 = new Subject("minishell", "minishell-pdf", "Its minishell", 4L);
        savedSubjects.add(subject1);
        savedSubjects.add(subject2);
        savedSubjects.add(subject3);
        savedSubjects.add(subject4);
        savedSubjects.add(subject5);
        savedSubjects.add(subject6);
        savedSubjects.add(subject7);
        savedSubjects.add(subject8);
        savedSubjects.add(subject9);
        savedSubjects.add(subject10);
        savedSubjects.add(subject11);
        return savedSubjects;
    }
}
