package com.sn.module.idempotent.exception;

public class IdempotentException extends RuntimeException {

    private String code= "1011";

    public IdempotentException(String message) {
        super(message);
    }
    public String getCode() {
        return code;
    }

}
