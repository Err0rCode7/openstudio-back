package com.codetogether.openstudio.controller;

import com.codetogether.openstudio.dto.subject.SubjectSaveRequestDto;
import com.codetogether.openstudio.dto.subject.SubjectUpdateRequestDto;
import com.codetogether.openstudio.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/subjects")
@RestController
public class SubjectsController {

    private final SubjectService subjectService;

    @PostMapping("")
    public Long save(@RequestBody SubjectSaveRequestDto requestDto) {
        return subjectService.save(requestDto);
    }

    @PutMapping("/{id}")
    public Long save(@PathVariable Long id, @RequestBody SubjectUpdateRequestDto requestDto) {
        return subjectService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        subjectService.delete(id);
        return id;
    }
}
