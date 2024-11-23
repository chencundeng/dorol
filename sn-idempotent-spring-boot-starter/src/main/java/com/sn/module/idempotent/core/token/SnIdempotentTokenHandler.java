package com.sn.module.idempotent.core.token;

import com.sn.module.idempotent.annotation.SnIdempotent;
import com.sn.module.idempotent.core.ISnIdempotentHandler;
import com.sn.module.idempotent.enums.SnIdempotentSceneEnum;
import com.sn.module.idempotent.enums.SnIdempotentTypeEnum;
import com.sn.module.idempotent.exception.IdempotentException;
import jakarta.servlet.http.HttpServletRequest;
import jodd.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token类型处理器
 */
public class SnIdempotentTokenHandler implements ISnIdempotentHandler, ITokenService {

    Logger log = LoggerFactory.getLogger(SnIdempotentTokenHandler.class);
    private static final String TOKEN_KEY = "idempotentToken";
    private static final String TOKEN_PREFIX_KEY = "sn:idempotent:token:";
    private static final long TOKEN_EXPIRED_TIME = 60;
    private static final Integer IS_EXXIST = 1;

    @Autowired
    private RedissonClient redisson;

    @Override
    public void execute(ProceedingJoinPoint joinPoint, SnIdempotent snIdempotent) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader(TOKEN_KEY);
        if (StringUtil.isEmpty(token)) {
            token = request.getParameter(TOKEN_KEY);
            if (StringUtil.isEmpty(token)) {
                throw new IdempotentException("幂等验证token不能为空！");
            }
        }
        Boolean result = redisson.getBucket(token).delete();
        if (!result) {
            System.out.println(result.toString());
            throw new IdempotentException("操作太快，请稍后再试！");
        }
    }

    @Override
    public String createToken() {
        String key = TOKEN_PREFIX_KEY + UUID.randomUUID();
        redisson.getBucket(key).set(IS_EXXIST, TOKEN_EXPIRED_TIME, TimeUnit.SECONDS);
        return key;
    }


    @Override
    public SnIdempotentSceneEnum getScene() {
        return SnIdempotentSceneEnum.API;
    }

    @Override
    public SnIdempotentTypeEnum getType() {
        return SnIdempotentTypeEnum.TOKEN;
    }
}
