package com.codetogether.openstudio.service;

import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.dto.pool.PoolListResponseDto;
import com.codetogether.openstudio.dto.pool.PoolSaveRequestDto;
import com.codetogether.openstudio.repository.PoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PoolService {

    private final PoolRepository poolRepository;

    @Transactional
    public Long save(PoolSaveRequestDto requestDto) {
        return poolRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<PoolListResponseDto> findByDateBetween(LocalDateTime date) {
        return poolRepository.findByDateBetween(date).stream()
                .map(PoolListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PoolListResponseDto> findAllDesc() {
        return poolRepository.findAllDesc().stream()
                .map(PoolListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PoolListResponseDto> findAllDesc(Pageable pageable) {
        Page<Pool> poolPage = poolRepository.findAllDesc(pageable);
        int totalElements = (int) poolPage.getTotalElements();
        return new PageImpl<>(poolPage
                .getContent().stream()
                .map(PoolListResponseDto::new)
                .collect(Collectors.toList()), pageable, totalElements);
    }

    @Transactional
    public void deleteById(Long id) {
        Pool pool = poolRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 풀입니다. + id" + id));
        poolRepository.deleteById(id);
    }
}
