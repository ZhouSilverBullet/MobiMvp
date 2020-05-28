package com.mobi.feature;

import android.content.Context;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;

import com.mobi.NetworkConst;
import com.mobi.utils.GetDeviceId;
import com.mobi.utils.SpNetworkUtil;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 14:45
 * @Dec 略
 */
public class DeviceIdRunnable implements Runnable {
    private Context mContext;
    private OnDeviceCallback onDeviceCallback;

    public DeviceIdRunnable(Context context, OnDeviceCallback deviceCallback) {
        mContext = context;
        this.onDeviceCallback = deviceCallback;
    }

    @Override
    public void run() {
        //获取保存在sd中的 设备唯一标识符
        String readDeviceID = GetDeviceId.readDeviceID(mContext);
        Log.e("设备标识", "readDeviceID" + readDeviceID);
        //获取缓存在  sharepreference 里面的 设备唯一标识
        String string = (String) SpNetworkUtil.getString(NetworkConst.KEY_DEVICE_ID);
        Log.e("设备标识", "string" + readDeviceID);
        String deviceID = "";
        if (!TextUtils.isEmpty(string)) {
            deviceID = string;
        } else if (!TextUtils.isEmpty(readDeviceID)) {
            deviceID = readDeviceID;
        }
        //判断 app 内部是否已经缓存,  若已经缓存则使用app 缓存的 设备id
        if (string != null) {
            //app 缓存的和SD卡中保存的不相同 以app 保存的为准, 同时更新SD卡中保存的 唯一标识符
            if (TextUtils.isEmpty(readDeviceID) && !string.equals(readDeviceID)) {
                // 取有效地 app缓存 进行更新操作
                if (TextUtils.isEmpty(readDeviceID) && !TextUtils.isEmpty(string)) {
                    readDeviceID = string;
                    GetDeviceId.saveDeviceID(readDeviceID, mContext);
                }
            }
        }
        // app 没有缓存 (这种情况只会发生在第一次启动的时候)
        if (TextUtils.isEmpty(readDeviceID)) {
            //保存设备id
            readDeviceID = GetDeviceId.getDeviceId(mContext);
            deviceID = readDeviceID;
        }
        //左后再次更新app 的缓存
        GetDeviceId.saveDeviceID(readDeviceID, mContext);
        SpNetworkUtil.putString(mContext, NetworkConst.KEY_DEVICE_ID, readDeviceID);
        Log.e("最终设备标识", "string" + readDeviceID);

        if (onDeviceCallback != null) {
            onDeviceCallback.onDevice(deviceID);
        }
    }

    public interface OnDeviceCallback {
        @WorkerThread
        void onDevice(String deviceId);
    }
}
