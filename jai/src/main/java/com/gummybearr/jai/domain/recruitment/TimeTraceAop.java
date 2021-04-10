package com.gummybearr.jai.domain.recruitment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
public class TimeTraceAop {

    Logger logger = Logger.getGlobal();

    @Around("execution(* com.gummybearr.jai.domain..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        logger.log(Level.INFO, "Start: " + String.valueOf(joinPoint));
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            logger.log(Level.INFO, "End: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
