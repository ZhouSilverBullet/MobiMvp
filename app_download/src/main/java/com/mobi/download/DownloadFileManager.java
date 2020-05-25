package com.mobi.download;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2020/5/25 18:36
 * Version: 1.0
 * Description:
 */
public class DownloadFileManager {
    public static void download(final String path, final String targetFilePath, final int threadCount, final IDownloadFileCallBack callBack) {
        final FileDownload fileDownload = new FileDownload(path, targetFilePath, threadCount);
        fileDownload.setCallBack(callBack);
        new Thread(fileDownload).start();
    }

    public static void download(String path, String targetFilePath, IDownloadFileCallBack callBack) {
        download(path, targetFilePath, 3, callBack);
    }
}
