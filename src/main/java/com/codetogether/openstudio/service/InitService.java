package com.codetogether.openstudio.service;

import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Subject;
import com.codetogether.openstudio.dto.pool.PoolListResponseDto;
import com.codetogether.openstudio.dto.pool.PoolSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InitService {
    private final PoolService poolService;
    private final SubjectService subjectService;

    public int createWeeklyPools() {
        LocalDateTime now = LocalDateTime.now();
        List<PoolListResponseDto> listResponseDtos = poolService.findByDateBetween(now);
        if (listResponseDtos.size() == 0) {
            return subjectService.findAllDesc().stream()
                    .map((entity) -> {
                        Subject subject = new Subject(entity.getName(), entity.getPdfRef(), entity.getDescription(), entity.getCircle());
                        Pool pool = new Pool(subject, now.plusDays(7));
                        PoolSaveRequestDto requestDto = new PoolSaveRequestDto(pool);
                        poolService.save(requestDto);
                        return requestDto;
                    })
                    .collect(Collectors.toList()).size();
        } else {
            return listResponseDtos.size();
        }
    }

    public List<Subject> initSubjectTable() {
        List<Subject> preAllSubjects = subjectService.findAllEntity();
        if (preAllSubjects.size() == 0) {
            List<Subject> sampleSubjects = Subject.getSampleSubjects();
            for (Subject subject : sampleSubjects) {
                subjectService.save(subject);
            }
            return sampleSubjects;
        } else {
            return preAllSubjects;
        }
    }
}
