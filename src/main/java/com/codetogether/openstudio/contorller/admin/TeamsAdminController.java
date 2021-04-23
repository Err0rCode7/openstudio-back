package com.codetogether.openstudio.contorller.admin;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/admin/teams")
@Controller
public class TeamsAdminController {

    private final TeamService teamService;

    @GetMapping("")
    public String dashboardPage(Model model, @LoginUser SessionUser user, @PageableDefault Pageable pageable) {
        model.addAttribute("currentPage", "team");
        model.addAttribute("userName", user.getName());
        model.addAttribute("teams", teamService.findAllDesc(pageable));

        return "admin/teams";
    }

    @GetMapping("/save")
    private String teamSavePage(Model model) {
        model.addAttribute("currentPage", "team");
        return "admin/teams-save";
    }
}
