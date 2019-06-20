package com.timkey.demo.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author tangfeng
 * @fileName VaildateCodeException
 * @date 2019/6/19 18:05
 * @description 自定义异常
 */
public class VaildateCodeException extends AuthenticationException {
    public VaildateCodeException(String msg) {
        super(msg);
    }
}
