package org.mine.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Component
@Log4j
public class LogAdvice {
	@Before( "execution(* org.mine.service.AOPSampleService*.doAdd(String, String)) && args(str1,str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info(str1 + "=============");
		log.info(str2 + "=============");
	}
	@AfterThrowing(pointcut = "execution(* org.mine.service.AOPSampleService*.*(..))", throwing = "exception")
	public void logException(Exception exception) {
		log.info("exception");
		log.info(exception);
	}
	
	@Around("execution(* org.mine.service.AOPSampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		log.info("Target : " + pjp.getTarget());
		log.info("Param : " + Arrays.toString(pjp.getArgs()));
		Object result = null;
		try {
			result = pjp.proceed();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		log.info("Time : " + (end - start));
		return result;
		
	}
}
