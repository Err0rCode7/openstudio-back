package com.codetogether.openstudio.contorller.page;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.dto.CommonResponseDto;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.dto.page.Page1ResponseDto;
import com.codetogether.openstudio.dto.page.Page2ReservationRequestDto;
import com.codetogether.openstudio.dto.page.Page2ResponseDto;
import com.codetogether.openstudio.dto.page.Page3ResponseDto;
import com.codetogether.openstudio.dto.reservation.ReservationRequestDto;
import com.codetogether.openstudio.exception.NoSuchSessionUserException;
import com.codetogether.openstudio.service.PageService;
import com.codetogether.openstudio.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PageController {

    private final PageService pageService;

    @GetMapping("/page-1")
    public Page1ResponseDto getPage1(@LoginUser SessionUser user) {
        if (user == null) {
            throw new NoSuchSessionUserException("존재하지 않는 세션의 유저입니다.");
        }
        return pageService.getPage1(user.getName());
    }

    @GetMapping("/page-2")
    public Page2ResponseDto getPage2() {
        return pageService.getPage2();
    }

    @PostMapping("/page-2")
    public CommonResponseDto reserve(@RequestBody Page2ReservationRequestDto requestDto, @LoginUser SessionUser user) {
        if (user == null) {
            throw new NoSuchSessionUserException("존재하지 않는 세션의 유저입니다.");
        }
        return pageService.saveReservation(requestDto);
    }

    @DeleteMapping("/page-2")
    public CommonResponseDto deleteReservaiton(@LoginUser SessionUser user) {
        if (user == null) {
            throw new NoSuchSessionUserException("존재하지 않는 세션의 유저입니다.");
        }
        return pageService.deleteReservationByUserName(user.getName());
    }

    @GetMapping("/page-3")
    public Page3ResponseDto getPage3() {
        return pageService.getPage3();
    }
}
