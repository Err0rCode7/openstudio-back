package com.codetogether.openstudio.controller.admin;

import com.codetogether.openstudio.domain.Subject;
import com.codetogether.openstudio.util.InitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

//@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class InitAdminController {

    private final InitService initService;

    public InitAdminController(InitService initService) {
        this.initService = initService;
    }

    @GetMapping("/init/pool")
    public int initPool() {
        return initService.createWeeklyPools();
    }

    @GetMapping("/init/subject")
    public List<Subject> initSubject() {
        return initService.initSubjectTable();
    }

    @GetMapping("/init/all")
    public String initAll() {
        List<Subject> subjects = initService.initSubjectTable();
        int weeklyPools = initService.createWeeklyPools();
        if (weeklyPools > 0 && subjects.size() > 0) {
            return "Success";
        } else {
            return "Fail";
        }
    }
}
