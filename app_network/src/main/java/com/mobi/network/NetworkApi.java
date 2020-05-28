package com.mobi.network;

import com.mobi.network.entity.TokenBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 17:28
 * @Dec 略
 */
public interface NetworkApi {
    @POST("{tokenPath}")
    Call<BaseResponse<TokenBean>> refreshToken(@Path("tokenPath") String path);

}
