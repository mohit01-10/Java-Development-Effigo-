package com.aopprojects.spring_aop.aopexample.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class LoggingAspect {

	
	private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	
	
	public void logMethodCall(JoinPoint joinPoint) {
		
		@Before()
		((org.slf4j.Logger) logger).info("Beffoe Aspect - Method call - {}",joinPoint);
		}
	}

