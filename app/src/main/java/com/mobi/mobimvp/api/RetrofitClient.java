package com.mobi.mobimvp.api;

import com.mobi.network.BaseRetrofitClient;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 20:46
 * @Dec 略
 */
public class RetrofitClient extends BaseRetrofitClient<ApiService> {

    private RetrofitClient() {
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

}
