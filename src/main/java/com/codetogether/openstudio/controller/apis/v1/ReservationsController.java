package com.codetogether.openstudio.controller.apis.v1;

import com.codetogether.openstudio.dto.reservation.ReservationListResponseDto;
import com.codetogether.openstudio.dto.reservation.ReservationRequestDto;
import com.codetogether.openstudio.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Api(tags = {"Reservation API"})
@RequiredArgsConstructor
@RequestMapping("api/v1/reservations/")
@RestController
public class ReservationsController {

    private final ReservationService reservationService;

    @ApiOperation(value = "현재 날짜 예약 내용 전체 조회", notes = "현재 날짜를 기준으로 예약 내용 전체를 조회하는 API입니다.")
    @GetMapping("")
    public List<ReservationListResponseDto> getReservationsByCurrentDate() {
        return reservationService.findByDateBetween(LocalDateTime.now());
    }

    @ApiOperation(value = "예약 요청", notes = "특정 Subject의 팀매칭을 예약요청하는 API입니다.")
    @PostMapping("")
    public Long save(
            @ApiParam(name = "예약 요청 정보", value = "Subject 이름, 유저 id", required = true)
            @RequestBody ReservationRequestDto requestDto) {
        return reservationService.save(requestDto);
    }

    @ApiOperation(value = "예약 취소 요청", notes = "예약 취소 요청하는 API입니다.")
    @DeleteMapping("{id}")
    public Long delete(
            @ApiParam(name = "예약 취소 요청 정보", value = "예약 id", required = true)
            @PathVariable Long id) {
        reservationService.delete(id);
        return id;
    }
}
