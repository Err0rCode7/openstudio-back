package com.codetogether.openstudio.controller;

import com.codetogether.openstudio.dto.reservation.ReservationListResponseDto;
import com.codetogether.openstudio.dto.reservation.ReservationRequestDto;
import com.codetogether.openstudio.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/reservations")
@RestController
public class ReservationsController {

    private final ReservationService reservationService;

    @GetMapping("")
    public List<ReservationListResponseDto> getReservationsByCurrentDate() {
        return reservationService.findByDateBetween(LocalDateTime.now());
    }

    @PostMapping("")
    public Long save(Model model, @RequestBody ReservationRequestDto requestDto) {
        return reservationService.save(requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        reservationService.delete(id);
        return id;
    }
}
