package com.mobi.feature;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/27 21:20
 * @Dec 用来获取 baseUrl
 */
public interface INetworkCallback {
    /**
     * 获取 一开始配置的时候的 Url
     *
     * @return
     */
    String getBaseConfigUrl();

    /**
     * 获取BaseUrl
     *
     * @return
     */
    String getBaseUrl();

    /**
     * 多个url的时候用来扩展
     * @return
     */
    @NonNull
    Map<String, Callback> getBaseUrlMap();

    /**
     * 获取deviceId
     *
     * @return
     */
    String getDeviceId();

    /**
     * 获取外界的token
     *
     * @return
     */
    String getToken();

    /**
     * 刷新外面的token
     * 这个方法会在 TokenInterceptor 中调用到
     * 通过 {@link NetworkConfig#setNewToken} 进行设置
     *
     * @param newToken
     */
    void refreshToken(@Nullable String newToken);


    public interface Callback {
        /**
         *
         * @return
         */
        String call();

        default void onCreateFail(String call) {

        }
    }
}
