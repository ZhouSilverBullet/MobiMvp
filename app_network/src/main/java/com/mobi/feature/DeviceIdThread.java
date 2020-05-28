package com.mobi.feature;

import android.support.annotation.WorkerThread;

import com.mobi.NetworkSession;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 14:58
 * @Dec 略
 */
public class DeviceIdThread extends Thread implements DeviceIdRunnable.OnDeviceCallback {

    private final DeviceIdRunnable deviceIdRunnable;

    private DeviceIdRunnable.OnDeviceCallback mDeviceCallback;

    public DeviceIdThread() {
        deviceIdRunnable = new DeviceIdRunnable(NetworkSession.get().getContext(), this);
    }

    public void setDeviceCallback(DeviceIdRunnable.OnDeviceCallback deviceCallback) {
        this.mDeviceCallback = deviceCallback;
    }

    @Override
    public void run() {
        deviceIdRunnable.run();
    }

    @WorkerThread
    @Override
    public void onDevice(String deviceId) {
        if (mDeviceCallback != null) {
            mDeviceCallback.onDevice(deviceId);
        }
    }
}
