package com.mobi.feature;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mobi.NetworkConst;
import com.mobi.utils.AppUtil;
import com.mobi.utils.SpNetworkUtil;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/27 20:50
 * @Dec 略
 */
public class NetworkConfig {

    private SSLSocketFactory sslSocketFactory;
    private HostnameVerifier hostnameVerifier;
    private List<Interceptor> extraInterceptor;

    /**
     * 按照 秒 来实现的
     */
    private int connectTimeout = 10_000;
    private int readTimeout = 10_000;
    private int writeTimeout = 10_000;


    /**
     * 分发器的线程池
     */
    private Dispatcher dispatcher;

    /**
     * loggingInterceptor
     */
    private Interceptor loggingInterceptor;

    /**
     * baseConfigUrl
     * baseUrl
     */
    private INetworkCallback networkCallback;
    private String baseConfigUrl;
    private String baseUrl;

    private String mac;
    private String androidId;
    private String imei;

    private String deviceId;

    private String token;

    private NetworkConfig(NetworkConfig.Builder builder) {

        if (builder.sslSocketFactory == null) {
            this.sslSocketFactory = DefualtSSLSocketClient.getSSLSocketFactory();
        } else {
            this.sslSocketFactory = builder.sslSocketFactory;
        }

        if (builder.hostnameVerifier == null) {
            this.hostnameVerifier = DefualtSSLSocketClient.getHostnameVerifier();
        } else {
            this.hostnameVerifier = builder.hostnameVerifier;
        }

        if (builder.extraInterceptor == null) {
            builder.extraInterceptor = new ArrayList<>(0);
        } else {
            this.extraInterceptor = builder.extraInterceptor;
        }

        if (builder.connectTimeout != 0) {
            this.connectTimeout = builder.connectTimeout;
        }

        if (builder.readTimeout != 0) {
            this.readTimeout = builder.readTimeout;
        }

        if (builder.writeTimeout != 0) {
            this.writeTimeout = builder.writeTimeout;
        }

        if (builder.dispatcher != null) {
            this.dispatcher = builder.dispatcher;
        }

        if (builder.loggingInterceptor != null) {
            this.loggingInterceptor = builder.loggingInterceptor;
        }

        this.networkCallback = builder.networkUrl;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public List<Interceptor> getExtraInterceptor() {
        return extraInterceptor;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public String getBaseConfigUrl() {
        if (networkCallback != null && TextUtils.isEmpty(baseConfigUrl)) {
            baseConfigUrl = networkCallback.getBaseConfigUrl();
        }

        return baseConfigUrl;
    }

    public String getBaseUrl() {
        if (networkCallback != null && TextUtils.isEmpty(baseUrl)) {
            baseUrl = networkCallback.getBaseUrl();
        }
        return baseUrl;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public Interceptor getLoggingInterceptor() {
        return loggingInterceptor;
    }

    public String getToken() {
        if (networkCallback != null) {
            token = networkCallback.getToken();
        }
        return token;
    }

    public void setNewToken(@NonNull String newToken) {
        if (networkCallback != null) {
            networkCallback.refreshToken(newToken);
        }
    }

    public String getImei() {
        if (TextUtils.isEmpty(imei)) {
            imei = AppUtil.getIMEI();
        }
        return imei;
    }

    public String getMac() {
        if (TextUtils.isEmpty(mac)) {
            mac = AppUtil.getMac();
        }
        return mac;
    }

    public String getAndroidId() {
        if (TextUtils.isEmpty(androidId)) {
            androidId = AppUtil.getAndroidID();
        }
        return androidId;
    }

    public String getDeviceId() {
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = SpNetworkUtil.getString(NetworkConst.KEY_DEVICE_ID);
            if (networkCallback != null && TextUtils.isEmpty(deviceId)) {
                deviceId = networkCallback.getDeviceId();
            }
        }
        return deviceId;
    }

    public static class Builder {
        SSLSocketFactory sslSocketFactory;
        HostnameVerifier hostnameVerifier;
        List<Interceptor> extraInterceptor;
        int connectTimeout;
        int readTimeout;
        int writeTimeout;
        Dispatcher dispatcher;

        INetworkCallback networkUrl;

        Interceptor loggingInterceptor;

        public Builder setSslSocketFactory(@Nullable SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        public Builder setHostnameVerifier(@Nullable HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder setExtraInterceptor(@Nullable List<Interceptor> extraInterceptor) {
            this.extraInterceptor = extraInterceptor;
            return this;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setNetworkCallback(@NonNull INetworkCallback networkCallback) {
            this.networkUrl = networkCallback;
            return this;
        }


        public Builder setDispatcher(@Nullable Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        public Builder setLoggingInterceptor(@Nullable Interceptor loggingInterceptor) {
            this.loggingInterceptor = loggingInterceptor;
            return this;
        }

        public NetworkConfig build() {
            return new NetworkConfig(this);
        }
    }

}
