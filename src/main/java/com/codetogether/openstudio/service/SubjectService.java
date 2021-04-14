package com.codetogether.openstudio.service;

import com.codetogether.openstudio.domain.Subject;
import com.codetogether.openstudio.dto.subject.SubjectListResponseDto;
import com.codetogether.openstudio.dto.subject.SubjectResponseDto;
import com.codetogether.openstudio.dto.subject.SubjectSaveRequestDto;
import com.codetogether.openstudio.dto.subject.SubjectUpdateRequestDto;
import com.codetogether.openstudio.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Transactional(readOnly = true)
    public SubjectResponseDto findById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 서브젝트가 없습니다. id = " + id));
        return new SubjectResponseDto(subject);
    }

    @Transactional(readOnly = true)
    public List<SubjectListResponseDto> findAllDesc() {
        return subjectRepository.findAllDesc().stream()
                .map(SubjectListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Subject> findAllEntity() {
        return subjectRepository.findAll();
    }

    @Transactional
    public Long save(SubjectSaveRequestDto requestDto) {
        return subjectRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long save(Subject subject) {
        return subjectRepository.save(subject).getId();
    }

    @Transactional
    public Long update(Long id, SubjectUpdateRequestDto requestDto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 서브젝트가 없습니다. id = " + id));
        subject.update(requestDto.getName(), requestDto.getPdfRef(), requestDto.getDescription(), requestDto.getCircle());
        return id;
    }

    public void delete(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 서브젝트가 없습니다. id = " + id));
        subjectRepository.deleteById(id);
    }
}
