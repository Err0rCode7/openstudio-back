package com.codetogether.openstudio.service;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.dto.member.MemberResponseDto;
import com.codetogether.openstudio.dto.member.MemberSaveRequestDto;
import com.codetogether.openstudio.dto.member.MemberUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

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

        MemberResponseDto responseDto = memberService.findById(savedMemberId);
        assertThat(responseDto.getName()).isEqualTo("member1");
        assertThat(responseDto.getEmail()).isEqualTo("email");
        assertThat(responseDto.getPicture()).isEqualTo("picture");
        assertThat(responseDto.getRole().name()).isEqualTo(Role.USER.name());
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

        MemberResponseDto responseDto = memberService.findById(updatedMemberId);
        assertThat(responseDto.getPicture()).isEqualTo("TestPicture");
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

        // Id로 조회시 잘못된 id로의 조회 예외가 발생하여야 한다.

        IllegalArgumentException temp = null;
        try {
            MemberResponseDto responseDto = memberService.findById(savedMemberId);
        } catch (Exception e) {
            if (e.getClass() == IllegalArgumentException.class) {
                temp = (IllegalArgumentException) e;
            }
        }
        assertThat(temp).isNotNull();
    }
}