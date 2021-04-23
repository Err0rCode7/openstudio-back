package com.codetogether.openstudio.contorller.admin;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.service.PoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@RequestMapping("/admin/pools")
@Controller
public class PoolsAdminController {

    private final PoolService poolService;

    @GetMapping("")
    public String dashboard(Model model, @LoginUser SessionUser user) {
        model.addAttribute("currentPage", "pool");
        model.addAttribute("userName", user.getName());
        model.addAttribute("pools", poolService.findAllDesc());

        return "admin/pools";
    }
}
