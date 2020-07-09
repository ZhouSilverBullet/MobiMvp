package com.mobi.mobimvp;

import android.app.Application;

import com.mobi.download.receiver.ConnectReceiver;
import com.mobi.global.MobiSession;
import com.mobi.thrid.images.loader.ImageLoader;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/26 15:09
 * @Dec 略
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ConnectReceiver.start(this);
        MobiSession.getInstance().init(this);
        ImageLoader.init(this);
    }
}
