package com.brian.admin.aspect;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Component
@Aspect
@ComponentScan
@EnableAspectJAutoProxy
@Slf4j
public class LogAspect {

    public void printDetail(JoinPoint point) {
        if (point == null) {
            return;
        }
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        //重新定义日志
        List<Object> requestArgs = new ArrayList<>();
        for (Object arg : point.getArgs()) {
            if ((arg instanceof ServletRequest) || (arg instanceof ServletResponse)) {
                continue;
            }
            requestArgs.add(arg);
        }
        log.info(" {}.{} - {}", className, methodName, JSONUtil.toJsonStr(requestArgs));
    }

    /**
     * 定义注解类型的切点，只要方法上有该注解，都会匹配
     */
    @Pointcut("execution(public * com.brian..*Controller.*(..))")
    public void webPoint() {
    }

    @Before("webPoint()")
    public void before(JoinPoint point) {
        this.printDetail(point);
    }
    @AfterReturning(pointcut = "webPoint()", returning = "res")
    public void afterWeb(JoinPoint point, Object res) {
        log.info("return:{}", res);
    }

}