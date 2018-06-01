package com.own.service.springboot;

import com.own.service.springboot.config.JettyConfiguration;
import com.own.service.springboot.config.WebMvcConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Import;

/**
 * Created by Spring on 2017/7/4.
 */
@Import({JettyConfiguration.class,WebMvcConfig.class})
public class BaseApplication {
    private static final Logger logger = LoggerFactory.getLogger(BaseApplication.class);

    static {
        // hack: 给com.alibaba.dubbo.common.logger.LoggerFactory设置环境变量:
        System.setProperty("dubbo.application.logger","slf4j");
    }
}
