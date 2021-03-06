package com.own.service.springboot.api.user;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

import com.own.service.springboot.api.APIConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Spring on 2017/7/4.
 */
public class UserIdentityInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(UserIdentityInterceptor.class);
    public static final String CURRENT_USER = "CURRENT_USER";

    @Autowired
    private UserIdentityService userIdentityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception{
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod method=(HandlerMethod)handler;
        MethodParameter[] methodParameters=method.getMethodParameters();
        boolean needAgentIdentity = false;
        for (MethodParameter methodParameter : methodParameters) {
            if (UserIdentity.class.isAssignableFrom(methodParameter.getParameterType())) {
                needAgentIdentity = true;
                break;
            }
        }
        if (!needAgentIdentity) {
            return true;
        }
        String token = Optional.fromNullable(request.getHeader(APIConstants.Headers.EBT_TOKEN))
                .or(Optional.fromNullable(request.getHeader(APIConstants.Headers.TOKEN)))
                .orNull();
        String agentId = Optional.fromNullable(request.getHeader(APIConstants.Headers.EBT_AGENT_ID))
                .or(Optional.fromNullable(request.getHeader(APIConstants.Headers.AGENT_ID)))
                .orNull();
        if (Strings.isNullOrEmpty(token)) {
            throw new UserException.TokenInvalidException("Token为空 : " + token);
        }
        UserIdentity userIdentity = null;
        try {
            //根据token 获取登录信息
            userIdentity = userIdentityService.get(token);
        } catch (Exception e) {
            logger.error("preHandle", e);
            throw e;
        }
        int userIdByToken = userIdentity.getAgentIdAsInt();
        if(userIdentity==null || userIdByToken<1){
            throw new UserException.TokenInvalidException("无效的Token : " + token);
        }
        // 传递了AgentId但跟Token不对应
        if (!Strings.isNullOrEmpty(agentId) && !agentId.equals(String.valueOf(userIdByToken))) {
            throw new UserException.TokenInvalidException("Token跟用户不匹配 : " + token);
        }
        request.setAttribute(CURRENT_USER, userIdentity);
        return true;
    }
}
