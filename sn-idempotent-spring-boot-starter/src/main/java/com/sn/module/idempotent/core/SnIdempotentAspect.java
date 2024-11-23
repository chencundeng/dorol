package com.sn.module.idempotent.core;

import com.sn.module.idempotent.annotation.SnIdempotent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * AOP增强
 * @author chennan
 * @version 1.0 2024-11-22
 */
@Aspect
public class SnIdempotentAspect {

    Logger log = LoggerFactory.getLogger(SnIdempotentAspect.class);
    @Autowired
    private SnIdempotentHandlerFactory snIdempotentHandlerFactory;

    @Around("@annotation(com.sn.module.idempotent.annotation.SnIdempotent)")
    public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {

        SnIdempotent snIdempotent = getIdempotent(joinPoint);
        ISnIdempotentHandler handler = snIdempotentHandlerFactory.getInstance(snIdempotent.scene(),snIdempotent.type());
        handler.execute(joinPoint,snIdempotent);

        Object result = joinPoint.proceed();

        return result;

    }

    public static SnIdempotent getIdempotent(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getMethod().getParameterTypes());
        return targetMethod.getAnnotation(SnIdempotent.class);
    }


}
