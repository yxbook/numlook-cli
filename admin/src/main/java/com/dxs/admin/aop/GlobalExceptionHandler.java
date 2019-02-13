package com.dxs.admin.aop;

import com.dxs.admin.enums.ResultCodeEnum;
import com.dxs.admin.exceptions.APIRunTimeException;
import com.dxs.admin.exceptions.RoleNotExistException;
import com.dxs.admin.result.RestResult;
import com.dxs.admin.result.ResultUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Mr.Dxs on 2018/7/14.
 * 全局异常 处理
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 处理 方法级别参数校验异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult bindExceptionHandler(ValidationException exception) {
        Map resultMap = new LinkedHashMap();
        if(exception instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) exception;

            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                // 获取请求方法路径
                String path = item.getPropertyPath().toString();
                String[] args = path.split("\\.");
                // 添加错误参数以及错误信息
                resultMap.put(args[args.length-1],item.getMessage());
            }
        }
        return ResultUtils.error(ResultCodeEnum.ILLEGAL_ARGUMENT.getCode(),
                        ResultCodeEnum.ILLEGAL_ARGUMENT.getMsg(),
                        resultMap);
    }


    /**
     * 业务逻辑 错误
     * @param e
     * @return
     */
    @ExceptionHandler(value = APIRunTimeException.class)

    @ResponseStatus(HttpStatus.OK)
    public RestResult runtimeExceptionHandler(APIRunTimeException e){
        return ResultUtils.error(e.getCode(),e.getMsg());
    }


    /**
     * 系统级别异常
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public RestResult systemExceptionHandler(Exception e){
        e.printStackTrace();
        return ResultUtils.error(1000,e.getMessage());
    }





}
