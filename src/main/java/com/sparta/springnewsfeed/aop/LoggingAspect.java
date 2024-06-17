package com.sparta.springnewsfeed.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private final HttpServletRequest request;

    public LoggingAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logRequestMethod(JoinPoint joinPoint) {
        String method = request.getMethod();
        String url = request.getRequestURL().toString();

        log.info("Request URL: {}, HTTP Method: {}", url, method);
        log.info("Handling method: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));
    }
}
