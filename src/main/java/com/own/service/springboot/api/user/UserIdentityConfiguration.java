package com.own.service.springboot.api.user;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Spring on 2017/7/5.
 */

@Configuration
@ConditionalOnMissingBean(UserIdentityService.class)
public class UserIdentityConfiguration {

    @Bean
    public UserIdentityService userIdentityService(){
        return new DefaultUserIdentityServiceImpl();
    }
}
