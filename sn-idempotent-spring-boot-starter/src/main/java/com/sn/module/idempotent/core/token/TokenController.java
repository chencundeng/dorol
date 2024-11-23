package com.sn.module.idempotent.core.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private ITokenService tokenService;

    @GetMapping("/get-token")
    public String createToken() {
        return tokenService.createToken();
    }

}
