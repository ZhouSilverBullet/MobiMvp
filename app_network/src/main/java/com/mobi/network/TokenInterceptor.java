package com.mobi.network;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.mobi.NetworkSession;
import com.mobi.network.entity.TokenBean;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/27 15:05
 * @Dec TokenInterceptor token 刷新使用
 */
public class TokenInterceptor implements Interceptor {
    private static final String TAG = "TokenInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response = chain.proceed(request);
        if (response.body() == null) {
            Log.e(TAG, "response.body = null");
            return response;
        }

        String string = response.body().string();

        try {
            Log.d(TAG, "response.code=" + string);
            BaseResponse baseResponse = new Gson().fromJson(string, BaseResponse.class);

            if (isTokenExpired(baseResponse)) {
                Log.e(TAG, "自动刷新Token,然后重新请求数据");
                //同步请求方式，获取最新的Token
                String newToken = getNewToken();
                Log.e(TAG, "intercept:新的请求头 " + newToken);

                //token 不能为null
                if (!TextUtils.isEmpty(newToken)) {
                    //使用新的Token，创建新的请求
                    Request newRequest = chain.request()
                            .newBuilder()
                            .header("Authorization", newToken)
                            .build();
                    //重新请求
                    return chain.proceed(newRequest);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //response.body().string() 只能被使用一次
        //https://www.jianshu.com/p/22cd955cf111
        return response.newBuilder()
                .body(ResponseBody.create(null, string))
                .code(200)
                .build();
    }

    //判断Token是否过期
    private boolean isTokenExpired(BaseResponse response) {
        if (response != null && "401".equals(response.getCode())) {
            return true;
        }
        return false;
    }

    //用同步方法获取新的Token
    private String getNewToken() throws IOException {
        // 通过获取token的接口，同步请求接口
        String token = null;
        retrofit2.Response<BaseResponse<TokenBean>> response = DefaultRetrofitClient.getInstance()
                .getHttpApi()
                .refreshToken(NetworkSession.get().getNetworkConfig().getTokenUrl())
                .execute();
        BaseResponse<TokenBean> body = response.body();
        if (body != null) {
            TokenBean data = body.getData();
            if (data != null) {
                token = data.getToken();
                //这里进行接口 回调出去，让外部知道刷新好的token
                if (!TextUtils.isEmpty(token)) {
                    NetworkSession.get().getNetworkConfig().setNewToken(token);
                }
            }
        }
        return token;
    }
}
