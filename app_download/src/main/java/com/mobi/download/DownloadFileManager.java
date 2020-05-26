package com.mobi.download;

import android.text.TextUtils;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2020/5/25 18:36
 * Version: 1.0
 * Description:
 */
public class DownloadFileManager {

    private IFileConnect fileConnect;
    private FileDownload fileDownload;
    private String path;
    private String targetFilePath;
    private int threadCount;
    private IDownloadFileCallBack callBack;

    private DownloadFileManager() {
    }

    public void init(IFileConnect connect) {
        fileConnect = connect;
    }

    private void setParams(String path,
                           String targetFilePath,
                           int threadCount,
                           IDownloadFileCallBack callBack) {
        this.path = path;
        this.targetFilePath = targetFilePath;
        this.threadCount = threadCount;
        this.callBack = callBack;
    }

    public void download(String path,
                         String targetFilePath,
                         int threadCount,
                         IDownloadFileCallBack callBack) {
        if (!TextUtils.isEmpty(this.targetFilePath)) {
            return;
        }
        setParams(path, targetFilePath, threadCount, callBack);

        fileDownload = new FileDownload(path, targetFilePath, threadCount, fileConnect);
        fileDownload.setCallBack(callBack);
        Thread thread = new Thread(fileDownload);
        thread.start();
    }

    public void startDownload() {
        if (TextUtils.isEmpty(targetFilePath)) {
            return;
        }
        fileDownload = new FileDownload(path, targetFilePath, threadCount, fileConnect);
        fileDownload.setCallBack(callBack);
        Thread thread = new Thread(fileDownload);
        thread.start();
    }

    public void pauseDownload() {
        if (TextUtils.isEmpty(targetFilePath)) {
            return;
        }
        if (fileDownload != null) {
            fileDownload.pause();
        }
    }

    public void release() {
        path = null;
        targetFilePath = null;
    }

    public void download(String path,
                         String targetFilePath,
                         IDownloadFileCallBack callBack) {
        download(path, targetFilePath, 3, callBack);
    }

    public static DownloadFileManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DownloadFileManager INSTANCE = new DownloadFileManager();
    }
}
