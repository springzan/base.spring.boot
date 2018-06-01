package com.own.service.springboot.api;

/**
 * Created by Spring on 2017/7/4.
 */
public class APIConstants {

    /**
     * 可忽略HTTP Header的大小写, 标准HTTP头不区分大小写: http://stackoverflow.com/questions/5258977/are-http-headers-case-sensitive
     */
    public interface Headers {
        // 前缀标准推荐
        String EBT_Authorization = "Authorization";
        String EBT_TOKEN = "Token";
        String EBT_AGENT_ID = "Agent-Id";

        // 兼容老版本
        String Authorization = "Authorization";
        String TOKEN = "Token";
        String AGENT_ID = "Agent-Id";

    }

    public interface StatParams {

        String APP_ID = "app_id";
        String APP_VERSION = "app_version";
        String APP_VERSION_NAME = "app_version_name";
        String APP_SOURCE = "app_source";
        String APP_OS = "app_os";
        String DEVICE_ID = "deviceId";

    }

}
