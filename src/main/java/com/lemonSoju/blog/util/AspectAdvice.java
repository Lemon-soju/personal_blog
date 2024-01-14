package com.lemonSoju.blog.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class AspectAdvice {

    @Pointcut("@annotation(com.lemonSoju.blog.annotation.Timer)")
    private void enableTimer(){}

    @Around("enableTimer()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();
        log.info("Executing method: " + joinPoint.getSignature().toString());
        log.info("total time : " + stopWatch.getTotalTimeSeconds());
        return result;
    }
}
