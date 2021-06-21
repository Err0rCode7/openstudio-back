package com.codetogether.openstudio.contorller.admin;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.dto.member.MemberListResponseDto;
import com.codetogether.openstudio.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/admin/members")
@Controller
public class MembersAdminController {

    private final MemberService memberService;

    @GetMapping("")
    public String dashboard(Model model, @LoginUser SessionUser user, @PageableDefault Pageable pageable) {
        model.addAttribute("currentPage", "member");
        model.addAttribute("userName", user.getName());
        model.addAttribute("members", memberService.findAllDesc(pageable));
        return "admin/members";
    }

    @GetMapping("/update/{id}")
    public String memberUpdatePage(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        model.addAttribute("currentPage", "member");
        model.addAttribute("userName", user.getName());
        model.addAttribute("member", memberService.findById(id));
        return "admin/members-update";
    }

    @GetMapping("/save")
    public String memberSavePage(Model model,  @LoginUser SessionUser user) {
        model.addAttribute("currentPage", "member");
        model.addAttribute("userName", user.getName());
        return "admin/members-save";
    }
}
