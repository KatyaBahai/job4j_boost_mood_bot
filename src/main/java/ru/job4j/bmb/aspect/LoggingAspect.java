package ru.job4j.bmb.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* ru.job4j.bmb.service.*.*(..))")
    private void serviceLayer() { }

    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.printf("Calling method: %s%n", joinPoint.getSignature().getName());
        List<Object> args = Arrays.stream(joinPoint.getArgs()).toList();
        args.forEach(System.out::println);
    }
}
