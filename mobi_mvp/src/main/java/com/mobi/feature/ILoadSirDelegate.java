package com.mobi.feature;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/7/9 20:35
 * @Dec 略
 */
public interface ILoadSirDelegate {
    /**
     * 自己实现 loadSir
     *
     * @return
     */
    @Nullable
    View loadSirBindView();

    /**
     * 点击再次加载
     */
    void preLoad();
}
