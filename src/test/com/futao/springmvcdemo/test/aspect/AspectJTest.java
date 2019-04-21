package com.futao.springmvcdemo.test.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author futao
 * Created on 2019-04-17.
 */
public class AspectJTest {
    @Test
    public void test1() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AspectJSpringConfig.class);
        DivService divService = applicationContext.getBean(DivService.class);
        divService.div(1, 1);
        System.out.println(StringUtils.repeat("==", 50));
        divService.div(1, 0);
    }
}

@ComponentScan("com.futao.springmvcdemo.test.aspect")
@Configuration
@EnableAspectJAutoProxy
class AspectJSpringConfig {

}

interface DivService {
    default int div(int a, int b) {
        return a / b;
    }
}

/**
 * 业务逻辑，目标方法
 */
@Slf4j
@Service
class DivServiceImpl implements DivService {
    @Override
    public int div(int a, int b) {
        log.info("div目标方法被执行...");
        return a / b;
    }
}

/**
 * LogAop
 */
@Component
@Slf4j
@Aspect
class LogAspect {

    @Pointcut("execution(public int com.futao.springmvcdemo.test.aspect.DivService.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint point) {
        log.info("【{}】方法执行前@Before,参数列表:【{}】", point.getSignature().getName(), point.getArgs());
    }

    /**
     * 无论目标方法是否成功，都会执行该方法
     *
     * @param point
     */
    @After(value = "pointCut()")
    public void after(JoinPoint point) {
        log.info("【{}】方法执行后@After", point.getSignature().getName());
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterReturning(JoinPoint point, Object result) {
        log.info("【{}】方法执行后正常返回@AfterReturning,返回值:【{}】", point.getSignature().getName(), result);
    }

    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void afterThrowing(JoinPoint point, Exception e) {
        log.error("【{}】方法执行后发生异常,异常信息为【{}】", point.getSignature().getName(), e.getMessage());
    }
}