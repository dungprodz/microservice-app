package com.example.userservice.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("within(com.example.userservice.controller.*) || within(com.example.userservice.controller.listener.*)")
    public void serviceMethodExecution() {
    }

    @Around(value = "serviceMethodExecution()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final long startTime = System.currentTimeMillis();

        final Object proceed = joinPoint.proceed();

        final long executionTime = System.currentTimeMillis() - startTime;

        LOGGER.warn(String.format("%s executed in %s ms", joinPoint.getSignature().getName(), executionTime));

        return proceed;
    }

}
