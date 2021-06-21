package com.codetogether.openstudio.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@Component
@Aspect
public class ControllerLogAspect {

    private final MyLogger myLogger;

    public ControllerLogAspect(MyLogger myLogger) {
        this.myLogger = myLogger;
    }

    public void logBefore(String className, String methodName) {
        log.info("[" + myLogger.getUuid() + "]" + "[" + myLogger.getIp() + "]" + "[Before Controller]" + "className: " + className + ", methodName: " + methodName);
    }

    @Before("execution(public * com.codetogether.openstudio.controller..*.*(..))")
    public void beforeController(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logBefore(className, methodName);
    }

    @Before("execution(public * com.codetogether.openstudio.service..*.*(..))")
    public void beforeService(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logBefore(className, methodName);
    }
}
