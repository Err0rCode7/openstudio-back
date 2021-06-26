package com.codetogether.openstudio.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
@Controller
public class CustomErrorController implements Err {

    @GetMapping("/denied-page")
    public String deniedPage() {
        return "denied-page";
    }

    @GetMapping("/error")
    public String notFoundPage() {
        return "not-found";
    }
}
