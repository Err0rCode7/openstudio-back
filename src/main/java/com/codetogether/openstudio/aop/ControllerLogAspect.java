package com.codetogether.openstudio.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

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
