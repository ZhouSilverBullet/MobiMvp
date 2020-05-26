package com.mobi.download.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.mobi.download.DownloadFileManager;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/26 14:59
 * @Dec 略
 */
public class ConnectReceiver extends BroadcastReceiver {

    private boolean isAvailable;

    public ConnectReceiver(Context context) {
        isAvailable = netAvailable(context);
    }

    /**
     * 启动网络监听
     *
     * @param context
     */
    public static void start(Context context) {
        if (context == null) {
            return;
        }

        ConnectReceiver connectReceiver = new ConnectReceiver(context.getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(connectReceiver, intentFilter);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("网络状态发生变化");

        boolean available = netAvailable(context);

//        if (available == isAvailable) {
//            return;
//        }

        if (available) {
            Toast.makeText(context, "当前网络可用", Toast.LENGTH_SHORT).show();
//            DownloadFileManager.getInstance().startFileDownload();
            DownloadFileManager.getInstance().startDownload();
        } else {
            Toast.makeText(context, "当前网络不可用", Toast.LENGTH_SHORT).show();
            DownloadFileManager.getInstance().pauseDownload();
        }
        isAvailable = available;

        System.out.println("DownloadThread 网络状态发生变化");
    }

    private boolean netAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable();
        }
        return false;
    }
}
