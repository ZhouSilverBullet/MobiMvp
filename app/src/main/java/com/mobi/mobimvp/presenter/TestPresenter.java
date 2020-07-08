package com.mobi.mobimvp.presenter;

import com.mobi.base.BaseRxPresenter;
import com.mobi.mobimvp.api.RetrofitClient;
import com.mobi.network.CommonSchedulers;
import com.mobi.network.callback.IRequestCallback;

import io.reactivex.disposables.Disposable;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/18 15:39
 * @Dec 略
 */
public class TestPresenter extends BaseRxPresenter {
    public void load() {
        Disposable disposable = CommonSchedulers.execHttp(RetrofitClient.getInstance().getHttpApi()
                .login(), new IRequestCallback<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFailure(int code, String error) {

            }
        });

        addSubscribe(disposable);
    }
}
