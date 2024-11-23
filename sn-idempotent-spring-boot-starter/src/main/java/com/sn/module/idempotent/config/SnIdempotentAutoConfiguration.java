package com.sn.module.idempotent.config;

import com.sn.module.idempotent.core.SnIdempotentAspect;
import com.sn.module.idempotent.core.SnIdempotentExpHandler;
import com.sn.module.idempotent.core.SnIdempotentHandlerFactory;
import com.sn.module.idempotent.core.SnIdempotentParamHandler;
import com.sn.module.idempotent.core.token.SnIdempotentTokenHandler;
import com.sn.module.idempotent.core.token.TokenController;
import org.springframework.context.annotation.Bean;

/**
 * 主动注入配置类
 * @author chennan
 * @version 1.0v 2024-11-23
 */
public class SnIdempotentAutoConfiguration {

    @Bean
    public SnIdempotentAspect snIdempotentAspect() {
        return new SnIdempotentAspect();
    }

    @Bean
    //@ConditionalOnMissingBean(SnIdempotentTokenHandler.class)
    public SnIdempotentTokenHandler snIdempotentTokenHandler() {
        return new SnIdempotentTokenHandler();
    }

    @Bean
    public TokenController tokenController() {
        return new TokenController();
    }

    @Bean
    public SnIdempotentExpHandler snIdempotentExpHandler() {
        return new SnIdempotentExpHandler();
    }

    @Bean
    public SnIdempotentParamHandler snIdempotentParamHandler() {
        return new SnIdempotentParamHandler();
    }

    @Bean
    public SnIdempotentHandlerFactory snIdempotentHandlerFactory() {
        return new SnIdempotentHandlerFactory();
    }

}
