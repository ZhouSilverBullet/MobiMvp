package com.mobi.mobimvp;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.mobi.NetworkSession;
import com.mobi.download.receiver.ConnectReceiver;
import com.mobi.feature.INetworkCallback;
import com.mobi.feature.NetworkConfig;
import com.mobi.global.MobiSession;
import com.mobi.thrid.images.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/26 15:09
 * @Dec 略
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ConnectReceiver.start(this);
        MobiSession.getInstance().init(this);
        ImageLoader.init(this);
        List<Interceptor> list= new ArrayList<>();
        list.add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(request);
            }
        });
        NetworkSession.get().init(this, true, new NetworkConfig.Builder()
                .setExtraInterceptor(list)
                .setNetworkCallback(new INetworkCallback() {
                    @Override
                    public String getBaseConfigUrl() {
                        return null;
                    }

                    @Override
                    public String getBaseUrl() {
                        return "http://updateapi.dev.findwxapp.com/";
                    }

                    @NonNull
                    @Override
                    public Map<String, String> getBaseUrlMap() {
                        Map<String, String> map = new ArrayMap<>();
                        map.put("update", "http://updateapi.dev.findwxapp.com/");
                        return map;
                    }

                    @Override
                    public String getDeviceId() {
                        return null;
                    }

                    @Override
                    public String getToken() {
                        return null;
                    }

                    @Override
                    public void refreshToken(@Nullable String newToken) {

                    }
                })
                .build());
    }
}
