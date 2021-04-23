package com.codetogether.openstudio.service;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Subject;
import com.codetogether.openstudio.dto.member.MemberSaveRequestDto;
import com.codetogether.openstudio.dto.pool.PoolListResponseDto;
import com.codetogether.openstudio.dto.subject.SubjectListResponseDto;
import com.codetogether.openstudio.dto.subject.SubjectSaveRequestDto;
import com.codetogether.openstudio.repository.PoolRepository;
import com.codetogether.openstudio.repository.SubjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PoolServiceTest {

    @Autowired
    PoolService poolService;

    @Autowired
    PoolRepository poolRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Transactional
    @Test
    @DisplayName("풀 정상 페이지별조회 테스트")
    void retrievePageTest() {
        // given
        // 풀이 N개가 있는 상황에서
        // 컨트롤러로부터 페이지 요청을 전달 받았다.
        // 페이지는 사이즈가 10이고 두 번째 페이지 요청이다.

        poolRepository.deleteAll();

        int N = 12;
        int pageSize = 10;
        int requestedPageNumber = 2 - 1;

        Subject subject1 = subjectRepository.save(new Subject("subject1", "1", "1", 1L));
        Pool save1 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));
        Pool save2 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));
        Pool save3 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));
        Pool save4 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));
        Pool save5 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));
        Pool save6 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));
        Pool save7 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));
        Pool save8 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));
        Pool save9 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));
        Pool save10 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));
        Pool save11 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));
        Pool save12 = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));

        Pageable pageable = PageRequest.of(requestedPageNumber, pageSize);

        // when
        // 내림차순으로 페이지를 조회하는 서비스를 통해 조회를하면
        Page<PoolListResponseDto> responseDtos = poolService.findAllDesc(pageable);

        // 12개중 두개의 풀이 조회되어야한다.
        assertThat(responseDtos.getNumberOfElements()).isEqualTo(2);
        List<PoolListResponseDto> content = responseDtos.getContent();

        assertThat(responseDtos.getPageable().getPageNumber()).isEqualTo(1);
        assertThat(responseDtos.getPageable().getOffset()).isEqualTo(10);
    }

    @Test
    @Transactional
    @DisplayName("Pool 정상 delete test")
    public void 정상_삭제테스트() {

        // given
        // 정상적으로 저장된 풀의 Id를 컨트롤러를 통해 전달 받았다.

        Subject subject1 = subjectRepository.save(new Subject("subject1", "1", "1", 1L));
        Pool pool = poolRepository.save(new Pool(subject1, LocalDateTime.now().plusDays(1)));


        // when
        // 풀 서비스가 해당 아이디의 멤버를 삭제를 하면
        poolService.deleteById(pool.getId());

        // Id로 조회가 되지 않아야한다.

        Optional<Pool> foundPool = poolRepository.findById(pool.getId());

        assertThat(foundPool.isEmpty()).isTrue();
    }
}