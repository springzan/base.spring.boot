package com.own.service.springboot.api.user;

/**
 * Created by Spring on 2017/7/5.
 */
public interface UserIdentityService <T extends UserIdentity> {

    /**
     * 根据Token获取UserIdentity;
     * 如需自定义请实现该类, 并做为spring bean注入到Spring容器里;
     * UserIdentityInterceptor会依赖此Bean
     *
     *
     * @param token
     * @return
     * @throws UserException: 如需抛出此异常,
     * 实现者需要自己设置ExceptionHandler, 否者统一由APIExceptionHandler来处理
     *
     */
    T get(String token) throws UserException;
}
