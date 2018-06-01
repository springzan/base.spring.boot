package com.own.service.springboot.api;

import com.google.common.collect.ImmutableMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class HealthyCheckController {

    private static final Logger logger = LoggerFactory.getLogger(HealthyCheckController.class);

    @GetMapping("/healthy_check")
    public Object healthyCheck() {

        return ImmutableMap.builder()
                .put("isHealthy", true)
                .build();
    }
}
