package com.mobi.feature;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.mobi.NetworkConst;
import com.mobi.utils.AppUtil;
import com.mobi.utils.SpNetworkUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private List<Interceptor> extraNetworkInterceptor;

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
    private Map<String, String> baseUrlMap;

    private String mac;
    private String androidId;
    private String imei;

    private String deviceId;

    private String token;
    private String systemId;

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
            this.extraInterceptor = new ArrayList<>(0);
        } else {
            this.extraInterceptor = builder.extraInterceptor;
        }

        if (builder.extraNetworkInterceptor == null) {
            this.extraNetworkInterceptor = new ArrayList<>(0);
        } else {
            this.extraNetworkInterceptor = builder.extraNetworkInterceptor;
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

        if (TextUtils.isEmpty(builder.systemId)) {
            this.systemId = "";
        } else {
            this.systemId = builder.systemId;
        }

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

    public List<Interceptor> getExtraNetworkInterceptor() {
        return extraNetworkInterceptor;
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

    public Map<String, String> getMapBaseUrl() {
        if (networkCallback != null && baseUrlMap == null) {
            baseUrlMap = networkCallback.getBaseUrlMap();
            if (baseUrlMap == null) {
                baseUrlMap = new ArrayMap<>();
            }
        }
        return baseUrlMap;
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
            if (TextUtils.isEmpty(token)) {
                token = "";
            }
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
                if (TextUtils.isEmpty(deviceId)) {
                    deviceId = "";
                }
            }
        }
        return deviceId;
    }

    public String getSystemId() {
        return systemId;
    }

    public static class Builder {
        SSLSocketFactory sslSocketFactory;
        HostnameVerifier hostnameVerifier;
        List<Interceptor> extraInterceptor;
        List<Interceptor> extraNetworkInterceptor;
        int connectTimeout;
        int readTimeout;
        int writeTimeout;
        Dispatcher dispatcher;

        INetworkCallback networkUrl;

        Interceptor loggingInterceptor;

        String systemId;

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

        public Builder setExtraNetworkInterceptor(@Nullable List<Interceptor> extraNetworkInterceptor) {
            this.extraNetworkInterceptor = extraNetworkInterceptor;
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

        public Builder setSystemId(String systemId) {
            this.systemId = systemId;
            return this;
        }

        public NetworkConfig build() {
            return new NetworkConfig(this);
        }
    }

}
