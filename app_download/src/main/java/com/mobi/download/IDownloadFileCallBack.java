package com.mobi.download;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2020/5/25 17:00
 * Version: 1.0
 * Description:
 */
public interface IDownloadFileCallBack {
    /**
     * 开始下载
     */
    void onStart();

    /**
     * 进度条
     *
     * @param progress
     */
    void onUpdateProgress(long progress);

    /**
     * 完成
     */
    void onFinished(String path);

    /**
     * 失败
     *
     * @param path
     * @param e
     */
    void onError(String path, Exception e);
}
