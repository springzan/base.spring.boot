package com.own.service.springboot.api.sign;

/**
 * Created by Spring on 2017/7/4.
 */
public class AuthorizationInfo {
    private String deviceId;
    private long signTimestamp;
    private String signVersion;
    private String sign;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getSignTimestamp() {
        return signTimestamp;
    }

    public void setSignTimestamp(long signTimestamp) {
        this.signTimestamp = signTimestamp;
    }

    public String getSignVersion() {
        return signVersion;
    }

    public void setSignVersion(String signVersion) {
        this.signVersion = signVersion;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "AuthorizationInfo{" +
                "deviceId='" + deviceId + '\'' +
                ", signTimestamp=" + signTimestamp +
                ", signVersion='" + signVersion + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
