package com.zwl.jyq.mvvm_stark.utils;

/**
 * author  Joy
 * Date:  2017/9/5.
 * version:  V1.0
 * Description:
 */
public class ServerException extends RuntimeException {
    private Integer code;
    private String message;

    public ServerException(String msg, Integer code) {
        super(msg);
        this.message = msg;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage(String message) {
        return message;
    }


}
