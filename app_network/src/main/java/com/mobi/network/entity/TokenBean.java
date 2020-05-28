package com.mobi.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 17:26
 * @Dec 略
 */
public class TokenBean {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("token")
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
