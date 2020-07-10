package com.mobi.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2019-12-05 14:34
 * Version: 1.0
 * Description:
 */
public enum LoadSirState {
    /**
     * SUCCESS 成功
     * ERROR   失败
     * EMPTY   空数据
     * LOADING 加载中
     * TIMEOUT 超时
     * CUSTOM  默认的一个界面
     */
    SUCCESS, ERROR, EMPTY, LOADING, TIMEOUT, CUSTOM;

    LoadSirState() {

    }

    public Class<? extends Callback> getCallback() {
        Class<? extends Callback> callback;
        switch (this) {
            case ERROR:
                callback = ErrorCallback.class;
                break;
            case EMPTY:
                callback = EmptyCallback.class;
                break;
            case LOADING:
                callback = LoadingCallback.class;
                break;
            case TIMEOUT:
                callback = TimeoutCallback.class;
                break;
            case CUSTOM:
                callback = CustomCallback.class;
                break;
            default:
                callback = SuccessCallback.class;
                break;
        }
        return callback;
    }
}
