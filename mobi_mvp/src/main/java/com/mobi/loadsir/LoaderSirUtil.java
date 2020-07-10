package com.mobi.loadsir;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.kingja.loadsir.core.LoadSir;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2020-01-06 16:46
 * Version: 1.0
 * Description:
 */
public class LoaderSirUtil {
    /**
     * 获取一个可以进行填写空描述的loadSir
     *
     * @param value
     * @return
     */
    public static LoadSir getLoaderSir(@Nullable String value) {
        if (TextUtils.isEmpty(value)) {
            return LoadSir.getDefault();
        }

        return new LoadSir.Builder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .addCallback(new EmptyNoDataCallback())
                .addCallback(new EmptyTextCallback(value))
                .setDefaultCallback(LoadingCallback.class)
                .build();
    }

}
