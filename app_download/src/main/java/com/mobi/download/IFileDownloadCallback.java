package com.mobi.download;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2020/5/25 17:00
 * Version: 1.0
 * Description: 这个给 FileDownload实现
 */
interface IFileDownloadCallback {

    /**
     * 进度条
     *
     * @param progress
     */
    void onUpdateProgress(long progress);

    /**
     * 完成
     */
    void onFinished();

    /**
     * 失败
     *
     * @param e
     */
    void onError(Exception e);
}
