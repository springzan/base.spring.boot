package com.own.service.springboot.api.user;

/**
 * Created by Spring on 2017/7/4.
 */
public class UserIdentity {
    private String agentId;
    private String token;

    public String getAgentId() {
        return agentId;
    }

    public int getAgentIdAsInt() {
        try {
            return Integer.parseInt(agentId);
        } catch (Exception e) {
            return 0;
        }
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public static UserIdentity build(String agentId, String token){
        UserIdentity agentIdentity = new UserIdentity();
        agentIdentity.setAgentId(agentId);
        agentIdentity.setToken(token);
        return agentIdentity;
    }
}
