package com.mobi.feature;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/27 21:20
 * @Dec 用来获取 baseUrl
 */
public interface INetworkCallback {
    String getBaseConfigUrl();

    String getBaseUrl();

    String getToken();

    String getDeviceId();
}
