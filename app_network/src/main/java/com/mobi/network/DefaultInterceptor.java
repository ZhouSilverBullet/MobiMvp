package com.mobi.network;

import android.content.Context;

import com.mobi.NetworkSession;
import com.mobi.utils.AppUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/27 20:36
 * @Dec 略
 */
public class DefaultInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Context context = NetworkSession.get().getContext();
        Request request = chain.request().newBuilder()
                .addHeader("Platform-No", "1")//平台号（1-安卓 2-ios）
                .addHeader("Device-Type", "mobile")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Device-ID", NetworkSession.get().getNetworkConfig().getDeviceId())
                .addHeader("X-Access-Token", NetworkSession.get().getNetworkConfig().getToken())
                .addHeader("Mac", NetworkSession.get().getNetworkConfig().getMac())
                .addHeader("Android-ID", NetworkSession.get().getNetworkConfig().getAndroidId())
                .addHeader("IMEI", NetworkSession.get().getNetworkConfig().getImei())
                .addHeader("package-name", context.getPackageName())
                .addHeader("Version", AppUtil.packageName(context))
                .addHeader("Channel", AppUtil.getChannelName(context))
                .addHeader("Device-Model", AppUtil.getSystemModel())//手机型号
                .addHeader("Device-Brand", AppUtil.getDeviceBrand())
                .addHeader("language", AppUtil.getLocale(context) == null ? "" : AppUtil.getLocale(context).getLanguage())
                .addHeader("Network-Type", AppUtil.getNetworkTypeName())
                .addHeader("Network-Operator", AppUtil.getSimOperatorInfo())
                .addHeader("Idfa", "")
                .build();
        Response response = chain.proceed(request);


        return response;
    }
}
