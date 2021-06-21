package com.codetogether.openstudio.aop;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {
    private String uuid;
    private String ip;
    private final HttpServletRequest httpServletRequest;

    public MyLogger(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public String getUuid() {
        return uuid;
    }

    public String getIp() {
        return ip;
    }

    @PostConstruct
    public void init() {
        this.uuid = UUID.randomUUID().toString();
        this.ip = httpServletRequest.getRequestURL().toString();
    }
}
