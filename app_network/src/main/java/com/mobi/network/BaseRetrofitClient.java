package com.mobi.network;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.mobi.NetworkSession;
import com.mobi.utils.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/27 15:56
 * @Dec 略
 */
public class BaseRetrofitClient<T> extends BaseOkhttpDalegate {
    public static final String TAG = "RetrofitClient";

    private volatile T mConfigApi;
    private volatile T mHttpApi;

    private Map<String, T> mApiMap;
    private Map<String, LazyCreateApiInterface<T>> mApiLazyMap;

    private Class<T> apiClass;

    private String mBaseUrl;
    private String mBaseConfigUrl;

    @Override
    protected void initConfigApi() {

        if (mConfigRetrofit == null) {
            synchronized (BaseRetrofitClient.class) {
                if (mConfigRetrofit == null) {
                    mConfigRetrofit = createNormalRetrofit(baseConfigUrl(), 0);
                }
            }
        }

    }

    @Override
    public String baseConfigUrl() {
        if (TextUtils.isEmpty(mBaseConfigUrl)) {
            mBaseConfigUrl = NetworkSession.get().getNetworkConfig().getBaseConfigUrl();
//            mBaseConfigUrl = invalidBaseUrl(mBaseConfigUrl);
        }
        return mBaseConfigUrl;
    }

    @Override
    public String baseUrl() {
        //本地存储一个，减少三目运算
        if (TextUtils.isEmpty(mBaseUrl)) {
            mBaseUrl = NetworkSession.get().getNetworkConfig().getBaseUrl();
            mBaseUrl = invalidBaseUrl(mBaseUrl);
        }
        LogUtils.d("mBaseUrl : " + mBaseUrl);
        return mBaseUrl;
    }

    @Override
    protected Map<String, String> baseUrlMap() {
        return NetworkSession.get().getNetworkConfig().getMapBaseUrl();
    }

    @Override
    protected void initApi() {
        if (mRetrofit == null) {
            synchronized (BaseRetrofitClient.class) {
                if (mRetrofit == null) {
                    mRetrofit = createNormalRetrofit(baseUrl(), 1);
                }
            }
        }
    }

    @Override
    protected synchronized void initApiMap() {
        mApiMap = new ArrayMap<>();
        mApiLazyMap = new ArrayMap<>();
        Map<String, String> map = baseUrlMap();
        if (map != null && map.size() > 0) {
            int i = 2;
            for (String key : map.keySet()) {
                int j = i;
                mApiLazyMap.put(key, new LazyCreateApiInterface<T>() {
                    @Override
                    public T create() {
                        return createNormalRetrofit(invalidBaseUrl(map.get(key)), j).create(getApiClass());
                    }
                });
                i++;
            }
        }
    }

    public T getConfigApi() {
        if (mConfigApi == null) {
            initConfigApi();
            mConfigApi = mConfigRetrofit.create(getApiClass());
        }
        return mConfigApi;
    }

    /**
     * 这个只能进行延迟初始化，因为这个时候BaseUrl还没有
     *
     * @return
     */
    public T getHttpApi() {
        if (mHttpApi == null) {
            initApi();
            mHttpApi = mRetrofit.create(getApiClass());
        }

        return mHttpApi;
    }

    /**
     * 通过map来进行设置，同时创建多个retrofit来进行
     *
     * @param key
     * @return
     */
    public T getHttpApi(String key) {
        if (mApiMap == null) {
            initApiMap();
        }

        T t = mApiMap.get(key);
        if (t == null) {
            LazyCreateApiInterface<T> apiInterface = mApiLazyMap.get(key);
            if (apiInterface != null) {
                t = apiInterface.create();
            }
        }
        return t;
    }

    private Class<T> getApiClass() {
        if (apiClass == null) {
            apiClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return apiClass;
    }

    /**
     * 用于延迟创建对应的baseUrl的api
     * @param <T>
     */
    public interface LazyCreateApiInterface<T> {
        T create();
    }
}
