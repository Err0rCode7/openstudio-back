package com.codetogether.openstudio.contorller;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.dto.member.MemberResponseDto;
import com.codetogether.openstudio.dto.member.MemberSaveRequestDto;
import com.codetogether.openstudio.dto.member.MemberUpdateRequestDto;
import com.codetogether.openstudio.repository.MemberRepository;
import com.codetogether.openstudio.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MembersController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/me")
    public MemberResponseDto getMember(@LoginUser SessionUser user) {
        if (user != null) {
            MemberResponseDto responseDto = memberService.findByEmail(user.getEmail());
            return responseDto;
        }
        return MemberResponseDto.builder()
                .name(null)
                .picture(null)
                .email(null)
                .build();
    }

    @PostMapping("")
    public Long save(@RequestBody MemberSaveRequestDto requestDto) {
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
