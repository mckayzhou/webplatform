/**
 * Copyright © 2016 本代码版权归周林波所有，严禁未经许可使用。
 *
 * @Title: logAop.java
 * @Prject: springweb
 * @Package: com.mckay.aop
 * @Description: TODO
 * @author: zhoulinbo
 * @date: 2016年12月19日 下午10:05:34
 * @version: V1.0
 */
package com.mckay.aop;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



/**
 * @ClassName: logAop
 * @Description: 日志处理类
 * @author: 周林波
 * @date: 2016年12月19日 下午10:05:34
 */
@Aspect
@Component
public class logAop {

	private Logger log=Logger.getLogger(logAop.class);

	@Pointcut("execution(* com.mckay.service.*(..))")
	public void aspect(){//定义一个切入点

	}

	//配置前置通知，使用在方法aspect()上注册的切入点，同时接受JoinPoint切入点对象，可以没有该参数
	@Before("aspect()")
	public void before(JoinPoint joinPoint ){

		String method=joinPoint.getSignature().getDeclaringTypeName();
		String args =joinPoint.getArgs().toString();
		log.info("开始执行"+method+"方法, 参数为： "+args);
	}

	//配置后置通知，使用在aspect()方法上注册的切入点
	@After("aspect()")
	public void after(JoinPoint joinPoint){
//		String method =joinPoint.getSignature().getDeclaringTypeName();
//		String result;
	}

	//配置环绕通知
	@Around("aspect()")
	public void around(ProceedingJoinPoint point) throws Throwable{
		String method=point.getSignature().getDeclaringTypeName();
		Object result=point.proceed();
		log.info("执行方法"+method+"的结果为： "+result);
	}

	//配置后置返回通知
	@AfterReturning("aspect()")
	public void afterReturn(JoinPoint joinPoint){

	}

	//配置抛出异常后的通知
	@AfterThrowing(pointcut="aspect()",throwing="e")
	public void afterThrow(JoinPoint joinPoint,Throwable e){
		String method=joinPoint.getSignature().getDeclaringTypeName();
		String params= JSON.toJSONString(joinPoint.getArgs()) ;

		log.error("<<<<<<<<<<<<<<<method"+method+"has exception>>>>>>>>>>>>>>");
		log.error("<<<<<<<<<<<<<<<param are"+params+">>>>>>>>>>>>>>>>>>>>>>>>");
		log.error("<<<<<<<<<<<<<<异常代码是："+e.getClass().getName()+">>>>>>>>>>");
		log.error("<<<<<<<<<<<<<<异常信息是："+e.getMessage()+">>>>>>>>>>>>>>>>>>");

	}

}
