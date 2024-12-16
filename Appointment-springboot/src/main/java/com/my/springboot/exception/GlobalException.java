package com.my.springboot.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

//自定义异常
@EqualsAndHashCode(callSuper = true)
@Data
public class GlobalException extends RuntimeException {
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    private Integer code;

    public GlobalException(String msg) {
        super(msg);
    }

    public GlobalException(int errorCode, String msg) {
        super(msg);
        this.code = errorCode;
    }

}