package com.sn.module.idempotent.core;

import com.sn.module.idempotent.annotation.SnIdempotent;
import com.sn.module.idempotent.enums.SnIdempotentSceneEnum;
import com.sn.module.idempotent.enums.SnIdempotentTypeEnum;
import com.sn.module.idempotent.exception.IdempotentException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;

import java.util.concurrent.TimeUnit;

/**
 * 唯一参数类型处理器
 * @author chennan
 * @version 1.0v 2024-11-23
 */
public class SnIdempotentParamHandler implements ISnIdempotentHandler {

    Logger log = LoggerFactory.getLogger(SnIdempotentParamHandler.class);
    private static final String PARAM_KEY = "param:";
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void execute(ProceedingJoinPoint joinPoint, SnIdempotent snIdempotent) {
        String paramKey = PARAM_KEY+DigestUtil.md5Hex(JSON.toJSONBytes(joinPoint.getArgs()));
        RLock lock = redissonClient.getLock(paramKey);
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
        return SnIdempotentTypeEnum.PARAM;
    }
}
