package com.own.service.springboot.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.own.service.springboot.api.APIExceptionHandler;
import com.own.service.springboot.api.ApiErrorController;
import com.own.service.springboot.api.CustomHttpMessageConverter;
import com.own.service.springboot.api.HealthyCheckController;
import com.own.service.springboot.api.sign.SignInterceptor;
import com.own.service.springboot.api.user.UserIdentityConfiguration;
import com.own.service.springboot.api.user.UserIdentityHandlerMethodArgumentResolver;
import com.own.service.springboot.api.user.UserIdentityInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by Spring on 2017/7/4.
 */
@Import({ApiErrorController.class,APIExceptionHandler.class,
        HealthyCheckController.class,UserIdentityConfiguration.class})
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    // 内部API, 不需要验签和用户身份认证策略
    public static final String API_PREFIX_VIP = "/vip";

    protected String[] whiteList=new String[]{"/error","/healthy_check",API_PREFIX_VIP};

    public String[] getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String[] whiteList) {
        this.whiteList = whiteList;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
        converters.add(getCustomHttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Bean
    public CustomHttpMessageConverter getCustomHttpMessageConverter(){
        CustomHttpMessageConverter c=new CustomHttpMessageConverter();
        FastJsonConfig config=new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
        c.setFastJsonConfig(config);
        return c;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserIdentityHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInterceptor())
                .excludePathPatterns(whiteList);
        registry.addInterceptor(userIdentityInterceptor())
                .excludePathPatterns(whiteList);
        super.addInterceptors(registry);
    }

    @Bean
    public HandlerInterceptor signInterceptor() {
        return new SignInterceptor();
    }

    @Bean
    public HandlerInterceptor userIdentityInterceptor() {
        return new UserIdentityInterceptor();
    }
}
