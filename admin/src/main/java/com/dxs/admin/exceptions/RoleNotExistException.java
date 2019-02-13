package com.dxs.admin.exceptions;

/**
 * Created by Mr.Dxs on 2018/7/17.
 */
public class RoleNotExistException extends APIRunTimeException {

    public RoleNotExistException(int code, String msg) {
        super(code, msg);
    }
}
