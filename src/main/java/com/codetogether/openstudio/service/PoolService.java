package com.codetogether.openstudio.service;

import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Subject;
import com.codetogether.openstudio.dto.pool.PoolListResponseDto;
import com.codetogether.openstudio.dto.pool.PoolResponseDto;
import com.codetogether.openstudio.dto.pool.PoolSaveRequestDto;
import com.codetogether.openstudio.repository.PoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PoolService {

    private final PoolRepository poolRepository;

    public Long save(PoolSaveRequestDto requestDto) {
        return poolRepository.save(requestDto.toEntity()).getId();
    }

    public List<PoolListResponseDto> findByDateBetween(LocalDateTime date) {
        return poolRepository.findByDateBetween(date).stream()
                .map(PoolListResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<PoolListResponseDto> findAllDesc() {
        return poolRepository.findAllDesc().stream()
                .map(PoolListResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        Pool pool = poolRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 풀입니다. + id" + id));
        poolRepository.deleteById(id);
    }
}
