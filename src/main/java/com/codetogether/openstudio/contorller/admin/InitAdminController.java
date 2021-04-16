package com.codetogether.openstudio.contorller.admin;

import com.codetogether.openstudio.domain.Subject;
import com.codetogether.openstudio.service.InitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class InitAdminController {

    private final InitService initService;

    public InitAdminController(InitService initService) {
        this.initService = initService;

        /**
         * 테스트용
         * InitController Bean 생성자 주입시 서브젝트와 풀을 만들어놓는다.
         */
        this.initService.initSubjectTable();
        this.initService.createWeeklyPools();
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
