package com.codetogether.openstudio.util;

import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Subject;
import com.codetogether.openstudio.repository.PoolRepository;
import com.codetogether.openstudio.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InitService {
    private final PoolRepository poolRepository;
    private final SubjectRepository subjectRepository;

    @PostConstruct
    private void init() {
        this.initSubjectTable();
        this.createWeeklyPools();
    }

    @Transactional
    public int createWeeklyPools() {
        LocalDateTime now = LocalDateTime.now();
        List<Pool> pools = poolRepository.findByDateBetween(now);
        if (pools.size() == 0) {
            return subjectRepository.findAllDesc().stream()
                    .map((entity) -> {
                        Pool pool = poolRepository.save(new Pool(entity, now.plusDays(7)));
                        return pool;
                    })
                    .collect(Collectors.toList()).size();
        } else {
            return pools.size();
        }
    }

    @Transactional
    public List<Subject> initSubjectTable() {
        List<Subject> preAllSubjects = subjectRepository.findAll();
        if (preAllSubjects.size() == 0) {
            List<Subject> sampleSubjects = Subject.getSampleSubjects();
            for (Subject subject : sampleSubjects) {
                subjectRepository.save(subject);
            }
            return sampleSubjects;
        } else {
            return preAllSubjects;
        }
    }
}
