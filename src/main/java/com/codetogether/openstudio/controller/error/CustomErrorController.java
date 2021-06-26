package com.codetogether.openstudio.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/denied-page")
    public String deniedPage() {
        return "denied-page";
    }

    @GetMapping("/error")
    public String notFoundPage() {
        return "error/404";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
