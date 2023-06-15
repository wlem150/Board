package org.mine.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
@Component
@Aspect
@Log4j

public class LogAdvice {
	@Before("execution(* org.mine.service.AOPSampleService*.doAdd(String,String)) && args(str1, str2)")
	public void logBefore(String str1, String str2) {
		log.info(str1 + "=======================");
		log.info(str2 + "=======================");
	}
	
	@AfterThrowing(pointcut = "execution(* org.mine.service.AOPSampleService*.*(..))", throwing = "exception")
	public void logException(Exception exception) {
		log.info("exception");
		log.info(exception);
	}
}
