package com.mobi.network;

import com.mobi.network.entity.TokenBean;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 11:56
 * @Dec 略
 */
public final class DefaultRetrofitClient extends BaseRetrofitClient<NetworkApi> {

    private DefaultRetrofitClient() {
    }

    public static DefaultRetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DefaultRetrofitClient INSTANCE = new DefaultRetrofitClient();
    }

}
