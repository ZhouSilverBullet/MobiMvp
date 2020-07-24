package com.mobi.mobimvp.api;

import android.util.Log;

import com.mobi.network.BaseRetrofitClient;

import retrofit2.Retrofit;

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

    @Override
    protected Retrofit createNormalRetrofit(String baseUrl, int type) {
        Log.e("RetrofitClient", "createNormalRetrofit type: " + type);
        return super.createNormalRetrofit(baseUrl, type);
    }

    public ApiService getUpdateApi() {
        return getHttpApi("update");
    }


}
