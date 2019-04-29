package com.zwl.jyq.mvvm_stark.http.service.bean;

import java.io.Serializable;

/**
 * author  Joy
 * Date:  2017/9/5.
 * version:  V1.0
 * Description:
 */
public class Respose<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
