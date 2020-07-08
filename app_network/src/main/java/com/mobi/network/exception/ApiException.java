package com.mobi.network.exception;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 21:09
 * @Dec 略
 */
public class ApiException extends Exception {
    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
