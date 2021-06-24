package com.codetogether.openstudio.controller.apis.v1;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.dto.CommonResponseDto;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.dto.page.Page1ResponseDto;
import com.codetogether.openstudio.dto.page.Page2ReservationRequestDto;
import com.codetogether.openstudio.dto.page.Page2ResponseDto;
import com.codetogether.openstudio.dto.page.Page3ResponseDto;
import com.codetogether.openstudio.exception.NoSuchSessionUserException;
import com.codetogether.openstudio.service.PageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Page API"})
@RequiredArgsConstructor
@RequestMapping("/api/v1/pages/")
@RestController
public class PageController {

    private final PageService pageService;

    @ApiOperation(value = "Page-1 Model 조회", notes = "Page-1의 모델을 조회하는 API입니다.")
    @GetMapping("page-1")
    public Page1ResponseDto getPage1(@LoginUser SessionUser user) {
        if (user == null) {
            throw new NoSuchSessionUserException("존재하지 않는 세션의 유저입니다.");
        }
        return pageService.getPage1(user.getName());
    }

    @ApiOperation(value = "Page-2 Model 조회", notes = "Page-2의 모델을 조회하는 API입니다.")
    @GetMapping("page-2")
    public Page2ResponseDto getPage2() {
        return pageService.getPage2();
    }

    @ApiOperation(value = "Page-2 매칭 예약", notes = "Page-2의 선택한 서브젝트의 매칭을 예약하는 API입니다.")
    @PostMapping("page-2")
    public CommonResponseDto reserve(
            @ApiParam(name = "예약 요청 정보", value = "Subject 이름, 유저 id", required = true)
            @RequestBody Page2ReservationRequestDto requestDto, @LoginUser SessionUser user) {
        if (user == null) {
            throw new NoSuchSessionUserException("존재하지 않는 세션의 유저입니다.");
        }
        return pageService.saveReservation(requestDto);
    }

    @ApiOperation(value = "Page-2 매칭 예약 제거", notes = "Page-2의 예약한 매칭을 제거하는 API입니다.")
    @DeleteMapping("page-2")
    public CommonResponseDto deleteReservaiton(@LoginUser SessionUser user) {
        if (user == null) {
            throw new NoSuchSessionUserException("존재하지 않는 세션의 유저입니다.");
        }
        return pageService.deleteReservationByUserName(user.getName());
    }

    @ApiOperation(value = "Page-3 Model 조회", notes = "Page-1의 모델을 조회하는 API입니다.")
    @GetMapping("page-3")
    public Page3ResponseDto getPage3() {
        return pageService.getPage3();
    }
}
