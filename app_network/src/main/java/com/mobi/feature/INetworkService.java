package com.mobi.feature;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/27 20:26
 * @Dec 略
 */
public interface INetworkService {

    String NETWORK_SERVICE = "network_service";

    /**
     * token
     * @return
     */
    String getToken();

    String getBaseUrl();

    String getBaseConfigUrl();
}
