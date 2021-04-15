package com.codetogether.openstudio.contorller.admin;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Subject;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.dto.pool.PoolListResponseDto;
import com.codetogether.openstudio.dto.pool.PoolSaveRequestDto;
import com.codetogether.openstudio.dto.subject.SubjectListResponseDto;
import com.codetogether.openstudio.dto.subject.SubjectResponseDto;
import com.codetogether.openstudio.repository.SubjectRepository;
import com.codetogether.openstudio.service.InitService;
import com.codetogether.openstudio.service.PoolService;
import com.codetogether.openstudio.service.SubjectService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class SubjectsAdminController {

    private final SubjectService subjectService;

    @GetMapping("/")
    public String getIndex(Model model, @LoginUser SessionUser user) {
        return "admin/index";
    }

    @GetMapping("/subjects")
    public String subjectsPage(Model model, @LoginUser SessionUser user) {
        List<SubjectListResponseDto> listResponseDtos = subjectService.findAllDesc();
        model.addAttribute("subjects", subjectService.findAllDesc());

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "admin/subjects";
    }

    @GetMapping("/subjects/save")
    public String subjectsSavePage(Model model) {
        return "admin/subjects-save";
    }

    @GetMapping("/subjects/update/{id}")
    public String subjectsUpdatePage(Model model, @PathVariable Long id) {
        model.addAttribute("subject", subjectService.findById(id));

        return "admin/subjects-update";
    }
}
