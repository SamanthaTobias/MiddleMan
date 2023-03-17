package com.example.middleman;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut("execution(* com.example.middleman.controller.*.*(..))")
	public void logEndpoint() {}

	@AfterReturning(pointcut = "logEndpoint()", returning = "returnValue")
	public void log(JoinPoint joinPoint, Object returnValue) {
		String methodParameters = Arrays.toString(joinPoint.getArgs());
		logger.info("Endpoint called: {}.{}() with parameters {} returned {}.",
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), methodParameters, returnValue);
	}


	@Around("execution(* com.example.middleman.controller.*.*(..))")
	public Object logControllerExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();

		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();

		logger.debug("{}#{} took {}ms", className, methodName, endTime - startTime);
		return result;
	}

}
