package com.mobi.mobimvp.presenter;

import com.mobi.base.BaseRxPresenter;
import com.mobi.mobimvp.api.RetrofitClient;
import com.mobi.network.BaseResponse;
import com.mobi.network.CommonSchedulers;
import com.mobi.network.callback.IRequestCallback;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/18 15:39
 * @Dec 略
 */
public class TestPresenter extends BaseRxPresenter {
    public void load() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("pkg_name", "com.mobi.clock");
        param.put("version", "1.0.0");
        param.put("channel","tencent");


        Disposable disposable = CommonSchedulers.execHttp(RetrofitClient.getInstance().getHttpApi()
                .checkVersioin(param), new IRequestCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> s) {

            }

            @Override
            public void onFailure(int code, String error) {

            }
        });

        addSubscribe(disposable);
    }
}
