package com.codetogether.openstudio.service;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.dto.member.MemberListResponseDto;
import com.codetogether.openstudio.dto.member.MemberResponseDto;
import com.codetogether.openstudio.dto.member.MemberSaveRequestDto;
import com.codetogether.openstudio.dto.member.MemberUpdateRequestDto;
import com.codetogether.openstudio.repository.MemberRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @DisplayName("Member 정상 페이지별 조회 test")
    public void 멤버_정상_페이지별_조회_테스트() {

        // given
        // 멤버가 N명이 있는 상황에서
        // 컨트롤러로부터 페이지 요청을 전달 받았다.
        // 페이지는 사이즈가 10이고 두 번째 페이지 요청이다.

        memberRepository.deleteAll();

        int N = 12;
        int pageSize = 10;
        int requestedPageNumber = 2 - 1;
        Member member1 = memberRepository.save(new Member("member1", "1", "1", Role.USER));
        Member member2 = memberRepository.save(new Member("member2", "2", "2", Role.USER));
        Member member3 = memberRepository.save(new Member("member3", "3", "3", Role.USER));
        Member member4 = memberRepository.save(new Member("member4", "4", "4", Role.USER));
        Member member5 = memberRepository.save(new Member("member5", "5", "5", Role.USER));
        Member member6 = memberRepository.save(new Member("member6", "6", "6", Role.USER));
        Member member7 = memberRepository.save(new Member("member7", "7", "7", Role.USER));
        Member member8 = memberRepository.save(new Member("member8", "8", "8", Role.USER));
        Member member9 = memberRepository.save(new Member("member9", "9", "9", Role.USER));
        Member member10 = memberRepository.save(new Member("member10", "10", "10", Role.USER));
        Member member11 = memberRepository.save(new Member("member11", "11", "11", Role.USER));
        Member member12 = memberRepository.save(new Member("member12", "12", "12", Role.USER));

        Pageable pageable = PageRequest.of(requestedPageNumber, pageSize);

        // when
        // 내림차순으로 페이지를 조회하는 서비스를 통해 조회를하면
        Page<MemberListResponseDto> responseDtos = memberService.findAllDesc(pageable);

        // 12명의 멤버 중 두명의 첫 번째와 두 번째 멤버가 조회되어야한다.

        assertThat(responseDtos.getNumberOfElements()).isEqualTo(2);
        List<MemberListResponseDto> content = responseDtos.getContent();
        int i = N - pageSize * requestedPageNumber;
        for (MemberListResponseDto memberListResponseDto : content) {
            assertThat(memberListResponseDto.getEmail()).isEqualTo(Integer.toString(i--));
        }
        assertThat(responseDtos.getPageable().getPageNumber()).isEqualTo(1);
        assertThat(responseDtos.getPageable().getOffset()).isEqualTo(10);
    }

    @Test
    @Transactional
    @DisplayName("Member 정상 save test")
    public void 멤버_정상_세이브테스트() {

        // given
        // dto를 컨트롤러를 통해 전달받았다.
        MemberSaveRequestDto dto = new MemberSaveRequestDto("member1", "email", "picture", Role.USER);

        // when
        // dto로 멤버세이브 서비스를 진행할때
        Long savedMemberId = memberService.save(dto);

        // Id로 멤버 조회시 해당 멤버가 있어야한다.

        Optional<Member> member = memberRepository.findById(savedMemberId);
        assertThat(member.isPresent()).isTrue();

        assertThat(member.get().getName()).isEqualTo("member1");
        assertThat(member.get().getEmail()).isEqualTo("email");
        assertThat(member.get().getPicture()).isEqualTo("picture");
        assertThat(member.get().getRole().name()).isEqualTo(Role.USER.name());
    }

    @Test
    @Transactional
    @DisplayName("Member 정상 update test")
    public void 멤버_정상_업데이트테스트() {

        // given
        // member를 저장하고
        // 컨트롤러를 통해 Member id와 멤버의 사진을 수정요청하는 dto를 전달 받았다.
        MemberSaveRequestDto dto = new MemberSaveRequestDto("member1", "email", "picture", Role.USER);
        Long savedMemberId = memberService.save(dto);

        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto("member1", "email", "TestPicture", Role.USER);

        // when
        // 멤버 서비스가 dto를 통해 수정을 하면
        Long updatedMemberId = memberService.update(savedMemberId, requestDto);

        // Id로 수정된 멤버 조회시 해당 멤버의 사진이 정상적으로 변경되어있어야 한다.

        Optional<Member> member = memberRepository.findById(updatedMemberId);

        assertThat(member.isPresent()).isTrue();

        assertThat(member.get().getPicture()).isEqualTo("TestPicture");
    }

    @Test
    @Transactional
    @DisplayName("Member 정상 delete test")
    public void 멤버_정상_삭제테스트() {

        // given
        // 정상적으로 저장된 멤버의 Id를 컨트롤러를 통해 전달 받았다.
        MemberSaveRequestDto dto = new MemberSaveRequestDto("member1", "email", "picture", Role.USER);
        Long savedMemberId = memberService.save(dto);

        // when
        // 멤버 서비스가 해당 아이디의 멤버를 삭제를 하면
        memberService.delete(savedMemberId);

        // Id로 조회가 되지 않아야한다.

        Optional<Member> member = memberRepository.findById(savedMemberId);

        assertThat(member.isEmpty()).isTrue();
    }
}