package com.codetogether.openstudio.controller.apis.v1;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.dto.member.MemberResponseDto;
import com.codetogether.openstudio.dto.member.MemberSaveRequestDto;
import com.codetogether.openstudio.dto.member.MemberUpdateRequestDto;
import com.codetogether.openstudio.exception.NoSuchSessionUserException;
import com.codetogether.openstudio.repository.MemberRepository;
import com.codetogether.openstudio.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Member API"})
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/")
@RestController
public class MembersController {

    private final MemberService memberService;

    @ApiOperation(value = "자신의 정보 확인", notes = "세션 값을 통해 자신의 정보를 확인하는 API입니다.")
    @GetMapping("me")
    public MemberResponseDto getMember(@LoginUser SessionUser user) {
        if (user == null) {
            throw new NoSuchSessionUserException("존재하지 않는 세션의 유저입니다.");
        }
        return memberService.findByEmail(user.getEmail());
    }

    @ApiOperation(value = "새로운 멤버 저장", notes = "새로운 유저를 저장하기위한 API입니다.")
    @PostMapping("")
    public Long save(
            @ApiParam(name = "저장할 멤버 정보", value = "이름, 이메일, 사진 URL, 권한", required = true)
            @RequestBody MemberSaveRequestDto requestDto) {
        return memberService.save(requestDto);
    }

    @ApiOperation(value = "기존 멤버 값 수정", notes = "기존 유저의 정보를 수정하기위한 API입니다.")
    @PutMapping("{id}")
    public Long update(
            @ApiParam(name = "수정할 멤버 id", value = "Member id", required = true, example = "1")
            @PathVariable Long id,
            @ApiParam(name = "유저의 변경 내용", value = "이름, 이메일, 사진 URL, 권한", required = true, example = "1")
            @RequestBody MemberUpdateRequestDto requestDto) {
        return memberService.update(id, requestDto);
    }

    @ApiOperation(value = "멤버 삭제", notes = "유저를 삭제하기위한 API입니다.")
    @DeleteMapping("{id}")
    public Long delete(
            @ApiParam(name = "삭제할 멤버 id", value = "Member id", required = true, example = "1")
            @PathVariable Long id) {
        memberService.delete(id);
        return id;
    }
}
