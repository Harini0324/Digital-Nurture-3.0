package com.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Advice for logging before method execution
    @Before("execution(* com.library..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Logging before method: " + joinPoint.getSignature().getName());
    }

    // Advice for logging after method execution
    @After("execution(* com.library..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("Logging after method: " + joinPoint.getSignature().getName());
    }
}
