package com.codetogether.openstudio.controller;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.dto.member.MemberResponseDto;
import com.codetogether.openstudio.dto.member.MemberSaveRequestDto;
import com.codetogether.openstudio.dto.member.MemberUpdateRequestDto;
import com.codetogether.openstudio.repository.MemberRepository;
import com.codetogether.openstudio.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MembersController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/me")
    public MemberResponseDto getMember(@LoginUser SessionUser user) {
        if (user == null) {
            return MemberResponseDto.builder()
                    .name(null)
                    .picture(null)
                    .email(null)
                    .build();
        }
        return memberService.findByEmail(user.getEmail());
    }

    @ApiOperation(value = "Member save", notes = "어드민이 유저를 저장하기위한 메소드입니다.")
    @PostMapping("")
    public Long save(
            @ApiParam(name = "저장할 멤버 정보", value = "이름, 이메일, 사진 URL, 권한", required = true)
            @RequestBody MemberSaveRequestDto requestDto) {
        return memberService.save(requestDto);
    }

    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody MemberUpdateRequestDto requestDto) {
        return memberService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        memberService.delete(id);
        return id;
    }
}
