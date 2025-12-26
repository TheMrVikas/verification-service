package com.rent.verify.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Description: this class is responsible for TODO
 * vikas
 * @created on 26 Dec 2025
 * @version 1.0
 */
@Component
@Aspect
public class LoggingAspect {

	private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	@Around("execution(* com.rent.verify..*(..))")
	    public Object trackMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

	        String className = joinPoint.getTarget().getClass().getSimpleName();
	        String methodName = joinPoint.getSignature().getName();
	        Object[] args = joinPoint.getArgs();

	        long startTime = System.currentTimeMillis();

	        log.info("**** ENTER | {}.{}() | args={}", className, methodName, Arrays.toString(args));

	        Object result = joinPoint.proceed();   // actual method call

	        long endTime = System.currentTimeMillis();

	        log.info("**** EXIT  | {}.{}() | time={} ms", 
	                 className, methodName, (endTime - startTime));

	        return result;
	    }
}