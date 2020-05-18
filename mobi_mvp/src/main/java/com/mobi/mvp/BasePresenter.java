package com.mobi.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.Nullable;

import com.mobi.util.LogUtils;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/18 15:05
 * @Dec 略
 */
public class BasePresenter<V extends IView> implements IPresenter<V>, LifecycleObserver {
    private static final String TAG = "BasePresenter";

    @Nullable
    protected V mView;

    @Override
    public void onAttach(V view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        if (mView != null) {
            mView = null;
        }
    }


    /// 下面生命周期，经过测试，比activity的对应的是先调用的 ///
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        LogUtils.i(TAG,  "-- onCreate --");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
//        LogUtils.i(TAG,  "-- onResume --");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
//        LogUtils.i(TAG,  "-- onPause --");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
//        LogUtils.i(TAG,  "-- onDestroy --");
    }
}