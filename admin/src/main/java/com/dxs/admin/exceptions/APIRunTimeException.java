package com.dxs.admin.exceptions;

/**
 * Created by Mr.Dxs on 2018/7/17.
 */
public class APIRunTimeException extends RuntimeException {

    private int code;

    private String msg;

    public APIRunTimeException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
