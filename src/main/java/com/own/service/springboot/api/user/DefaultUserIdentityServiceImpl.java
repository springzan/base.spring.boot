package com.own.service.springboot.api.user;

/**
 * Created by Spring on 2017/7/5.
 */
public class DefaultUserIdentityServiceImpl implements UserIdentityService<UserIdentity> {

    @Override
    public UserIdentity get(String token){
        //实际业务 获取登录信息
        int userId=0;
        return UserIdentity.build(String.valueOf(userId),token);
    }
}
