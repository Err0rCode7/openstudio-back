package com.codetogether.openstudio.contorller.admin;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.dto.subject.SubjectListResponseDto;
import com.codetogether.openstudio.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class SubjectsAdminController {

    private final SubjectService subjectService;

    @GetMapping("/")
    public String getIndex(Model model, @LoginUser SessionUser user) {
        model.addAttribute("userName", user.getName());
        model.addAttribute("currentPage", "home");
        return "admin/index";
    }

    @GetMapping("/subjects")
    public String subjectsPage(Model model, @LoginUser SessionUser user, @PageableDefault Pageable pageable) {
        model.addAttribute("currentPage", "subject");
        model.addAttribute("subjects", subjectService.findAllDesc(pageable));
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "admin/subjects";
    }

    @GetMapping("/subjects/save")
    public String subjectsSavePage(Model model) {
        model.addAttribute("currentPage", "subject");
        return "admin/subjects-save";
    }

    @GetMapping("/subjects/update/{id}")
    public String subjectsUpdatePage(Model model, @PathVariable Long id) {
        model.addAttribute("currentPage", "subject");
        model.addAttribute("subject", subjectService.findById(id));

        return "admin/subjects-update";
    }
}
