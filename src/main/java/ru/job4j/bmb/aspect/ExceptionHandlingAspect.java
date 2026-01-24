package ru.job4j.bmb.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    @AfterThrowing(pointcut = "execution(* ru.job4j.bmb..*.*(..))", throwing = "ex")
    public void handleException(JoinPoint joinPoint, Exception ex) {
        if (ex instanceof DataIntegrityViolationException) {
            LOGGER.warn("Duplicate user ignored: {}", ex.getMessage());
            return;
        }
        LOGGER.error("An error occurred: {}", ex.getMessage());
    }
}
