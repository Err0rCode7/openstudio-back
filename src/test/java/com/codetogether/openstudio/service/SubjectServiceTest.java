package com.codetogether.openstudio.service;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.domain.Subject;
import com.codetogether.openstudio.dto.member.MemberListResponseDto;
import com.codetogether.openstudio.dto.member.MemberResponseDto;
import com.codetogether.openstudio.dto.member.MemberSaveRequestDto;
import com.codetogether.openstudio.dto.member.MemberUpdateRequestDto;
import com.codetogether.openstudio.dto.subject.SubjectListResponseDto;
import com.codetogether.openstudio.dto.subject.SubjectSaveRequestDto;
import com.codetogether.openstudio.dto.subject.SubjectUpdateRequestDto;
import com.codetogether.openstudio.repository.MemberRepository;
import com.codetogether.openstudio.repository.SubjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubjectServiceTest {

    @Autowired
    SubjectService subjectService;

    @Autowired
    SubjectRepository subjectRepository;

    @Test
    @Transactional
    @DisplayName("subject 정상 페이지별 조회 test")
    public void 정상_페이지별_조회_테스트() {

        // given
        // 서브젝트가 N개가 있는 상황에서
        // 컨트롤러로부터 페이지 요청을 전달 받았다.
        // 페이지는 사이즈가 10이고 두 번째 페이지 요청이다.

        subjectRepository.deleteAll();

        int N = 12;
        int pageSize = 10;
        int requestedPageNumber = 2 - 1;
        Subject subject1 = subjectRepository.save(new Subject("subject1", "1", "1", 1L));
        Subject subject2 = subjectRepository.save(new Subject("subject2", "2", "2", 1L));
        Subject subject3 = subjectRepository.save(new Subject("subject3", "3", "3", 1L));
        Subject subject4 = subjectRepository.save(new Subject("subject4", "4", "4", 1L));
        Subject subject5 = subjectRepository.save(new Subject("subject5", "5", "5", 1L));
        Subject subject6 = subjectRepository.save(new Subject("subject6", "6", "6", 1L));
        Subject subject7 = subjectRepository.save(new Subject("subject7", "7", "7", 1L));
        Subject subject8 = subjectRepository.save(new Subject("subject8", "8", "8", 1L));
        Subject subject9 = subjectRepository.save(new Subject("subject9", "9", "9", 1L));
        Subject subject10 = subjectRepository.save(new Subject("subject10", "10", "10", 1L));
        Subject subject11 = subjectRepository.save(new Subject("subject11", "11", "11", 1L));
        Subject subject12 = subjectRepository.save(new Subject("subject12", "12", "12", 1L));

        Pageable pageable = PageRequest.of(requestedPageNumber, pageSize);

        // when
        // 내림차순으로 페이지를 조회하는 서비스를 통해 조회를하면
        Page<SubjectListResponseDto> responseDtos = subjectService.findAllDesc(pageable);

        // 12개의 서브젝트 중 두개의 첫 번째와 두 번째 서브젝트가 조회되어야한다.

        assertThat(responseDtos.getNumberOfElements()).isEqualTo(2);
        List<SubjectListResponseDto> content = responseDtos.getContent();
        int i = N - pageSize * requestedPageNumber;

        for (SubjectListResponseDto subjectListResponseDto : content) {
            assertThat(subjectListResponseDto.getPdfRef()).isEqualTo(Integer.toString(i--));
        }

        assertThat(responseDtos.getPageable().getPageNumber()).isEqualTo(1);
        assertThat(responseDtos.getPageable().getOffset()).isEqualTo(10);
    }

    @Test
    @Transactional
    @DisplayName("Subject 정상 save test")
    public void 정상_세이브테스트() {

        // given
        // dto를 컨트롤러를 통해 전달받았다.
        SubjectSaveRequestDto requestDto = new SubjectSaveRequestDto("subject1", "1", "1", 1L);

        // when
        // dto로 멤버세이브 서비스를 진행할때
        Long savedSubjectId = subjectService.save(requestDto);

        // Id로 멤버 조회시 해당 멤버가 있어야한다.

        Optional<Subject> subject = subjectRepository.findById(savedSubjectId);
        assertThat(subject.isPresent()).isTrue();

        assertThat(subject.get().getName()).isEqualTo("subject1");
        assertThat(subject.get().getPdfRef()).isEqualTo("1");
        assertThat(subject.get().getDescription()).isEqualTo("1");
        assertThat(subject.get().getCircle()).isEqualTo(1L);
    }


    @Test
    @Transactional
    @DisplayName("Subject 정상 update test")
    public void 업데이트테스트() {

        // given
        // subject를 저장하고
        // 컨트롤러를 통해 Member id와 멤버의 사진을 수정요청하는 dto를 전달 받았다.

        SubjectSaveRequestDto saveDto = new SubjectSaveRequestDto("subject1", "1", "1", 1L);
        Long savedSubjectId = subjectService.save(saveDto);

        SubjectUpdateRequestDto requestDto = new SubjectUpdateRequestDto(savedSubjectId, "subject1", "2", "2", 1L);

        // when
        // 서브젝트 서비스가 dto를 통해 수정을 하면
        Long updatedSubjectId = subjectService.update(savedSubjectId, requestDto);

        // Id로 수정된 멤버 조회시 변경사항이 정상적으로 적용되어있어야 한다.

        Optional<Subject> subject = subjectRepository.findById(savedSubjectId);
        assertThat(subject.isPresent()).isTrue();

        assertThat(subject.get().getPdfRef()).isEqualTo("2");
        assertThat(subject.get().getDescription()).isEqualTo("2");
    }

    @Test
    @Transactional
    @DisplayName("Subject 정상 delete test")
    public void 정상_삭제테스트() {

        // given
        // subject를 저장하고
        // 컨트롤러를 통해 Member id와 멤버의 사진을 수정요청하는 dto를 전달 받았다.
        SubjectSaveRequestDto saveDto = new SubjectSaveRequestDto("subject1", "1", "1", 1L);
        Long savedSubjectId = subjectService.save(saveDto);

        // when
        // 멤버 서비스가 해당 아이디의 멤버를 삭제를 하면
        subjectService.delete(savedSubjectId);

        // Id로 조회가 되지 않아야한다.

        Optional<Subject> subject = subjectRepository.findById(savedSubjectId);

        assertThat(subject.isEmpty()).isTrue();
    }
}