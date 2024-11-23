package com.sn.demo.request;

public class IdempotentTokenDTO {

    private String idempotentToken;
    private String userName;

    public String getIdempotentToken() {
        return idempotentToken;
    }

    public void setIdempotentToken(String idempotentToken) {
        this.idempotentToken = idempotentToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
