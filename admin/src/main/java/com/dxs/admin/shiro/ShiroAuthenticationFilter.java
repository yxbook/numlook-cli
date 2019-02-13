package com.dxs.admin.shiro;

import com.dxs.admin.constants.AdminConstant;
import com.dxs.admin.enums.ResultCodeEnum;
import com.dxs.admin.exceptions.APIRunTimeException;
import com.dxs.admin.result.RestResultBuilder;
import com.dxs.admin.result.ResultUtils;
import com.dxs.admin.utils.EncryptUtils;
import com.dxs.admin.utils.JWTUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by Mr.Dxs on 2018/6/4.
 * 自定义过滤器，用来实现用户 登录认证与授权认证
 */
public class ShiroAuthenticationFilter extends BasicHttpAuthenticationFilter {

    /**
     * 在此 对 Authorization(token) 为空、无效 情况进行处理
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        boolean flag = true;
        if (authorization == null) {
            flag = false;
        } else {
            try {
                Map params = JWTUtils.getClaims(AdminConstant.JWT_SALT, authorization);
            }catch (Exception e){
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 用户认证 此时 Authorization(token) 已经不可能为空、并且 token 已经是可以解析的（但是存在可能为 过期的token）
     * 主体提交认证
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception{
        HttpServletRequest req = (HttpServletRequest) request;

        // 获取 token
        String authorization = req.getHeader("Authorization");

        // TODO: 解析 authorization   初始化 ShiroToken
        Map params = JWTUtils.getClaims(AdminConstant.JWT_SALT, authorization);
        if (null == params) {
            // 账号不存在
            throw new UnknownAccountException();
        }
        String account = (String) params.get("ukAccount");
        // 设置 account ==》 account + authorization， 把 authorization 传递到Realm中 进行认证
        ShiroToken token = new ShiroToken(account + ":" + authorization);

        // 主体 提交认证  交给 realm 进行认证
        getSubject(request, response).login(token);

        // 如果没有异常抛出 则代表登录认证成 返回 true
        return true;
    }


    /**
     * 验证登录 提交认证
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean flag = false;
        if (isLoginAttempt(request, response)) {
            try {
                flag = executeLogin(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            flag = false;
            responseResult(request,response,ResultCodeEnum.TOKEN_EXPIRE.getCode(), ResultCodeEnum.TOKEN_EXPIRE.getMsg());
        }
        return flag;
    }

    /**
     * 对跨域提供支持
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-control-Allow-Origin", req.getHeader("Origin"));
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        resp.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            resp.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }


    public static void responseResult(ServletRequest request, ServletResponse response, int code, String msg){
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter outWriter = null;
        try {
            outWriter = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            if (-1 ==code){
                outWriter.write(mapper.writeValueAsString(ResultUtils.error(1, msg)));
            }else {
                outWriter.write(mapper.writeValueAsString(ResultUtils.error(code, msg)));
            }
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
