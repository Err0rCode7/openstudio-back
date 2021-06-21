package com.codetogether.openstudio.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/denied-page")
@Controller
public class ErrorController {

    @GetMapping("")
    public String deniedPage() {
        return "denied-page";
    }
}
