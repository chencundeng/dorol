package com.sn.module.idempotent.core;

import com.sn.module.idempotent.annotation.SnIdempotent;
import com.sn.module.idempotent.enums.SnIdempotentSceneEnum;
import com.sn.module.idempotent.enums.SnIdempotentTypeEnum;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 幂等验证处理接口
 * @author chennan
 * @version 1.0 2024-11-23
 */
public interface ISnIdempotentHandler {

    /**
     * 执行幂等性处理逻辑
     * @param joinPoint
     * @param snIdempotent
     */
    public void execute(ProceedingJoinPoint joinPoint, SnIdempotent snIdempotent);

    public SnIdempotentSceneEnum getScene();

    public SnIdempotentTypeEnum getType();


}
