package com.codetogether.openstudio.config.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class WebAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ade) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        System.out.println("AccessDeniedException ");
        request.setAttribute("msg", "접근권한 없는 사용자입니다.");
        request.setAttribute("nextPage", "/login");
        Arrays.stream(request.getCookies()).forEach(cookie -> {
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });
        request.getRequestDispatcher("/denied-page").forward(request, response);
    }
}
