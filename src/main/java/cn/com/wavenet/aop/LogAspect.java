package cn.com.wavenet.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

@Component
@Aspect  
public class LogAspect {
	
    private static Logger logger = Logger.getLogger(LogAspect.class);
	@Pointcut("execution(* cn.com.wavenet.activiti.service.waterCaseService..*(..))")  
    public void appointment(){}
    
    @Before("appointment()")  
    public void beforeAppointment(JoinPoint joinPoint){  
    	//获取方法名  
        String methodName = joinPoint.getSignature().getName(); 
        String args = "";
        for (int i = 0; i < joinPoint.getArgs().length; i++) {  
            args = (String) joinPoint.getArgs()[i];  
        }
        logger.debug("请求方法名："+methodName);
        logger.debug("请求参数："+args);
    }  
    
    @After("appointment()")  
    public void after(){  
        logger.debug("响应请求返回数据");
    }  
    
//    @Around("appointment()")  
//    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{  
//        System.out.println("进入环绕通知");  
//        Object object = pjp.proceed();//执行该方法  
//        System.out.println("退出方法");  
//        return object;  
//    }  
}
