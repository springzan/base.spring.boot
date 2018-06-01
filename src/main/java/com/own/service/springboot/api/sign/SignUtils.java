package com.own.service.springboot.api.sign;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ExecutionError;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Spring on 2017/7/4.
 */
public class SignUtils {
    public static final int SIGN_EXPIRE_SECONDS = 10;
    private static final Logger logger = LoggerFactory.getLogger(SignUtils.class);
    private static final Map<String, String> SIGN_KEY_MAP = ImmutableMap.<String, String>builder()
            .put("v1.1", "ios-zyj-v1.1")
            .put("zyj-iOS-1.4", "4S44-SS-4446544")
            .put("zyj-Android-1.4", "8-rind8rid-n7rd87-i")
            .put("zyj-iOS-2.0.0", "4S44-SS-4446544")
            .put("zyj-Android-2.0.0", "8-rind8rid-n7rd87-i")
            .build();

    /**
     * 从请求中解析AuthorizationInfo
     */
    public static AuthorizationInfo parseAuthInfo(HttpServletRequest request) throws SignException {
        String authorization = request.getHeader("EBT-Authorization");
        AuthorizationInfo authInfo = null;
        try {
            if (authorization != null) {
                authInfo = JSON.parseObject(authorization, AuthorizationInfo.class);
            }
        } catch (ExecutionError e) {
            logger.error("parseAuthInfo {}", authorization, e);
        }
        return authInfo;
    }

    /**
     * 验证签名是否过期;
     *
     * @return 如果过期, 返回true
     */
    public static boolean checkTimeout(AuthorizationInfo authInfo){
        if(null==authInfo){
            return false;
        }
        try {
            long signTimestamp=authInfo.getSignTimestamp();
            long tenSecondAgo= LocalDateTime.now().minusSeconds(SIGN_EXPIRE_SECONDS).toEpochSecond(ZoneOffset.ofHours(8))*1000;
            return signTimestamp < tenSecondAgo;
        }catch (Exception e){
            logger.error("checkTimeOut",e);
        }
        return false;
    }

    /**
     * 验证签名是否合法;
     *
     * @return 如果合法, 返回true
     */
    public static boolean checkSign(AuthorizationInfo authInfo) {
        if (authInfo == null) {
            return false;
        }
        String signVersion = authInfo.getSignVersion();
        String originKey = getOriginKeyBySignVersion(signVersion);
        if (originKey == null) {
            return false;
        }
        String sign = authInfo.getSign();
        String deviceId = authInfo.getDeviceId();
        long signTimestamp = authInfo.getSignTimestamp();
        String requestKey = md5(deviceId + originKey);
        String serverSign = md5(deviceId + signTimestamp + requestKey);
        return serverSign.equals(sign);
    }

    private static String getOriginKeyBySignVersion(String signVersion) {
        return SIGN_KEY_MAP.get(signVersion);
    }

    private static String md5(String string) {
        return DigestUtils.md5DigestAsHex(string.getBytes(Charsets.UTF_8));
    }

    public static void main(String[] args) {
        printOriginKey();
    }

    /**
     * 根据 Version 生成对应key
     */
    private static void printOriginKey() {
        String androidVersion = "iOS-1.0.0";
        String iosVersion = "Android-1.0.0";
        String androidKey = genRandomOriginKey(androidVersion);
        String iosKey = genRandomOriginKey(iosVersion);
        System.out.println(androidVersion + "|" + androidKey);
        System.out.println(iosVersion + "|" + iosKey);
        System.out.println(String.format(".put(\"%s\", \"%s\")", androidVersion, androidKey));
        System.out.println(String.format(".put(\"%s\", \"%s\")", iosVersion, iosKey));
    }

    public static String genRandomOriginKey(String version) {
        Random random = new Random();
        int ran = random
                .nextInt(10000);
        char[] chars = (version + ran).toCharArray();
        for (int i = 0; i < chars.length; i++) {
            ran = random.nextInt(chars.length);
            chars[i] = chars[ran];
        }
        return new String(chars);
    }
}
