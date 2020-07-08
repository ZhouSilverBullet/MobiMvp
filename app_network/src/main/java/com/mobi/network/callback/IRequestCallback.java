package com.mobi.network.callback;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 20:51
 * @Dec 略
 */
public interface IRequestCallback<T> {
    void onSuccess(T t);
    void onFailure(int code, String error);
}
