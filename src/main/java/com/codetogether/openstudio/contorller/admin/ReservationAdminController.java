package com.codetogether.openstudio.contorller.admin;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.service.ReservationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/admin/reservations")
@Controller
public class ReservationAdminController {

    private final ReservationService reservationService;

    @GetMapping("")
    public String dashboard(Model model, @LoginUser SessionUser user) {
        model.addAttribute("userName", user.getName());
        model.addAttribute("reservations", reservationService.findAllDesc());
        return "admin/reservations";
    }

    @GetMapping("/save")
    private String reservationSavePage(Model model) {
        return "admin/reservations-save";
    }
}
