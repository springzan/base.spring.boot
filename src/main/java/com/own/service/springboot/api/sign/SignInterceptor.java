package com.own.service.springboot.api.sign;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Spring on 2017/7/4.
 */
public class SignInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception{
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod method=(HandlerMethod)handler;
        if(null!=method.getBeanType().getAnnotation(NoNeedSign.class)){
            return true;
        }
        if(null!=method.getMethodAnnotation(NoNeedSign.class)){
            return true;
        }
        AuthorizationInfo authInfo=SignUtils.parseAuthInfo(request);
        if(null==authInfo){
            throw new SignException.SignParamIllegalException("签名信息缺少必要参数");
        }
        if (!SignUtils.checkSign(authInfo)) {
            throw new SignException("签名不正确");
        }
        if (SignUtils.checkTimeout(authInfo)) {
            throw new SignException.SignTimeoutException("签名超时，签名时间跟当前时间间隔太长，需要重签");
        }
        return super.preHandle(request,response,handler);
    }
}
