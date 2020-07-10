package com.mobi.global;

import android.content.Context;

import com.kingja.loadsir.core.LoadSir;
import com.mobi.loadsir.CustomCallback;
import com.mobi.loadsir.EmptyCallback;
import com.mobi.loadsir.ErrorCallback;
import com.mobi.loadsir.LoadingCallback;
import com.mobi.loadsir.TimeoutCallback;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/18 15:32
 * @Dec 略
 */
public class MobiSession {
    /**
     * debug 模式默认是true
     */
    private boolean isDebug = true;
    private Context mContext;

    private MobiSession() {
    }

    public static MobiSession getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 传入外部的application 对象
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context.getApplicationContext();
        //捕获异常
        CrashHandler.getInstance().init(mContext);

        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public Context getContext() {
        return mContext;
    }

    private static class SingletonHolder {
        private static final MobiSession INSTANCE = new MobiSession();
    }
}
