package com.mobi.mobimvp.api;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 20:45
 * @Dec 略
 */
public interface ApiService {
    @GET
    Observable<String> login();
}
