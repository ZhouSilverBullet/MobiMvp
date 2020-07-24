package com.mobi.mobimvp.api;

import com.mobi.network.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 20:45
 * @Dec 略
 */
public interface ApiService {
    @GET
    Observable<String> login();

    /**
     * 检查版本更新
     *
     * @return
     */
    @Headers("Content-Type: application/json")
    @POST("version-mgr/v1/version/check")
    Observable<BaseResponse<Object>> checkVersioin(@Body Map<String, Object> bean);
}
