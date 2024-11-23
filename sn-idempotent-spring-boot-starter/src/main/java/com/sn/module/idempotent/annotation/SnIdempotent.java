package com.sn.module.idempotent.annotation;

import com.sn.module.idempotent.enums.SnIdempotentSceneEnum;
import com.sn.module.idempotent.enums.SnIdempotentTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 幂等注释
 * @author chennan
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SnIdempotent {

    /**
     * 触发幂等失败逻辑时，返回的错误提示信息
     */
    String message() default "您操作太快，请稍后再试！";

    /**
     * 幂等性校验场景，API接口/MQ消息
     */
    SnIdempotentSceneEnum scene() default SnIdempotentSceneEnum.API;


    /**
     * 幂等性校验类型
     */
    SnIdempotentTypeEnum type() default SnIdempotentTypeEnum.TOKEN;

    /**
     * 去重标识
     */
    String key() default "";

}
