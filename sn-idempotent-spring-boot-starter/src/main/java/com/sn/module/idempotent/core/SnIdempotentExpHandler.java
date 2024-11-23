package com.sn.module.idempotent.core;

import com.sn.module.idempotent.annotation.SnIdempotent;
import com.sn.module.idempotent.enums.SnIdempotentSceneEnum;
import com.sn.module.idempotent.enums.SnIdempotentTypeEnum;
import com.sn.module.idempotent.exception.IdempotentException;
import jakarta.servlet.http.HttpServletRequest;
import jodd.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.TimeUnit;

/**
 *  表达式处理器
 * @author chennan
 * @version 1.0 2024-11-23
 */
public class SnIdempotentExpHandler implements ISnIdempotentHandler {

    Logger log = LoggerFactory.getLogger(SnIdempotentExpHandler.class);
    private static final String EXP_KEY = "exp:";
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void execute(ProceedingJoinPoint joinPoint, SnIdempotent snIdempotent) {

        String key = snIdempotent.key();
        if (StringUtil.isEmpty(key)) {
            throw new IdempotentException("去重标识不能为空！");
        }

        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Object keyValue = request.getParameter(key);
        if (null == keyValue || StringUtil.isEmpty(keyValue.toString())) {
            throw new IdempotentException("去重标识值不能为空！");
        }

        String expKey = EXP_KEY+keyValue;
        RLock lock = redissonClient.getLock(expKey);
        try {
            if(!lock.tryLock(120, TimeUnit.SECONDS)) {
                throw new IdempotentException("操作太频繁，请稍后再试");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public SnIdempotentSceneEnum getScene() {
        return SnIdempotentSceneEnum.API;
    }

    @Override
    public SnIdempotentTypeEnum getType() {
        return SnIdempotentTypeEnum.EXP;
    }
}
