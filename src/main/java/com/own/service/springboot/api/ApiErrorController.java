package com.own.service.springboot.api;

import com.own.service.springboot.util.SpringMvcUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Spring on 2017/7/5.
 */

/**
 * 处理spring mvc框架处理的错误, 比如:
 *
 * <p>
 * 1. 路径不对;
 * 2. http方法不对;
 * 3. 缺少必要参数;
 * 4. etc
 */
@RestController
@RequestMapping("/error")
public class ApiErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ApiErrorController.class);
    private final ErrorAttributes errorAttributes;

    @Autowired
    public ApiErrorController(ErrorAttributes errorAttributes){
        Assert.notNull(errorAttributes,"ErrorAttributes must not be null");
        this.errorAttributes=errorAttributes;
    }

    @Override
    public String getErrorPath(){
        return "/error";
    }

    @RequestMapping
    public ErrorApiResponse error(HttpServletRequest request){
        boolean needExceptionTrace= SpringMvcUtil.getTraceParameter(request);
        Map<String,Object> body=getErrorAttributes(request,needExceptionTrace);
        String trace = SpringMvcUtil.mapGetAsString(body, "trace");
        String status = SpringMvcUtil.mapGetAsString(body, "status");
        String error = SpringMvcUtil.mapGetAsString(body, "error");
        String path = SpringMvcUtil.mapGetAsString(body, "path");
        StringBuilder message = new StringBuilder()
                .append(SpringMvcUtil.mapGetAsString(body, "message"))
                .append(" : ")
                .append(path);
        ErrorApiResponse errorResponse = ErrorApiResponse.error();
        errorResponse.setCode(status);
        errorResponse.setType(error);
        if (needExceptionTrace) {
            message.append("\n\r")
                    .append(trace);
        }
        logger.error("{}", errorResponse);
        errorResponse.setMessage(message.toString());
        return errorResponse;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(aRequest);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}
