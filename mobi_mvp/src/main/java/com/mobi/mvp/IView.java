package com.mobi.mvp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/18 13:24
 * @Dec 略
 */
public interface IView {
    /**
     * 用于rxjava生命周期监听, dialog显示
     *
     * @return
     */
    @Nullable
    Activity getMvpActivity();
}
