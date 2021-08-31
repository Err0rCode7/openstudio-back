package com.codetogether.openstudio.service;

import com.codetogether.openstudio.domain.Subject;
import com.codetogether.openstudio.dto.subject.SubjectListResponseDto;
import com.codetogether.openstudio.dto.subject.SubjectResponseDto;
import com.codetogether.openstudio.dto.subject.SubjectSaveRequestDto;
import com.codetogether.openstudio.dto.subject.SubjectUpdateRequestDto;
import com.codetogether.openstudio.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<SubjectListResponseDto> findAllDesc(Pageable pageable) {
        Page<Subject> subjectPage = subjectRepository.findAllDesc(pageable);
        int totalElements = (int) subjectPage.getTotalElements();
        return new PageImpl<>(subjectPage
                .getContent().stream()
                .map(SubjectListResponseDto::new)
                .collect(Collectors.toList()), pageable, totalElements);
    }

    @Transactional(readOnly = true)
    public List<Subject> findAllEntity() {
        return subjectRepository.findAll();
    }

    @Transactional
    public Long save(SubjectSaveRequestDto requestDto) {
        subjectRepository.findByName(requestDto.getName())
                .ifPresent((subject -> {
                    throw new IllegalArgumentException("같은 이름의 서브젝트가 이미 존재합니다.");
                }));
        return subjectRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long save(Subject subject) {
        subjectRepository.findByName(subject.getName())
                .ifPresent((entity -> {
                    throw new IllegalArgumentException("같은 이름의 서브젝트가 이미 존재합니다.");
                }));
        return subjectRepository.save(subject).getId();
    }

    @Transactional
    public Long update(Long id, SubjectUpdateRequestDto requestDto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 서브젝트가 없습니다. id = " + id));
        subject.update(requestDto.getName(), requestDto.getPdfRef(), requestDto.getDescription(), requestDto.getCircle());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 서브젝트가 없습니다. id = " + id));
        subjectRepository.deleteById(id);
    }
}
