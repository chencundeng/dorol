package com.sn.module.idempotent.core;

import com.sn.module.idempotent.enums.SnIdempotentSceneEnum;
import com.sn.module.idempotent.enums.SnIdempotentTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class SnIdempotentHandlerFactory {

    @Autowired
    private ApplicationContext applicationContext;

    public ISnIdempotentHandler getInstance(SnIdempotentSceneEnum sceneEnum, SnIdempotentTypeEnum typeEnum) {
        ISnIdempotentHandler handler = null;
        Map<String, ISnIdempotentHandler> handlerMap = applicationContext.getBeansOfType(ISnIdempotentHandler.class);

        for (Map.Entry<String, ISnIdempotentHandler> entry : handlerMap.entrySet()) {
            ISnIdempotentHandler value = entry.getValue();
            if (value.getScene() == sceneEnum && value.getType() == typeEnum) {
                handler = value;
                break;
            }
        }
        return handler;
    }

}
