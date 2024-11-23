package com.sn.demo.controller;

import com.sn.demo.request.IdempotentExpDTO;
import com.sn.demo.request.IdempotentParamDTO;
import com.sn.demo.request.IdempotentTokenDTO;
import com.sn.module.idempotent.annotation.SnIdempotent;
import com.sn.module.idempotent.enums.SnIdempotentSceneEnum;
import com.sn.module.idempotent.enums.SnIdempotentTypeEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 幂等性校验例子
 * @author chennan
 * @version 1.0 2024-11-22
 */
@RestController
public class IdempotentController {


    @SnIdempotent(scene = SnIdempotentSceneEnum.API,type = SnIdempotentTypeEnum.TOKEN)
    @PostMapping("/idempotent/token")
    public String tokenIdempotent(IdempotentTokenDTO idempotentTokenDTO) {
        return idempotentTokenDTO.getUserName();
    }

    @SnIdempotent(scene = SnIdempotentSceneEnum.API,type = SnIdempotentTypeEnum.PARAM)
    @PostMapping("/idempotent/param")
    public String paramIdempotent(IdempotentParamDTO idempotentParamDTO) {
        return idempotentParamDTO.getUserName();
    }

    @SnIdempotent(scene = SnIdempotentSceneEnum.API,type = SnIdempotentTypeEnum.EXP,key = "key")
    @PostMapping("/idempotent/exp")
    public String expIdempotent(IdempotentExpDTO idempotentExpDTO) {
        return idempotentExpDTO.getUserName();
    }

}
