package com.lxb.springboot_vue_.exception;

import lombok.Getter;

/**
 * @author XBlib
 * @version 1.0
 */
@Getter
public class ServiceException extends RuntimeException{
    private String code;
    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
