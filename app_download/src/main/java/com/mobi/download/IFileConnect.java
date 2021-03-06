package com.mobi.download;

import android.support.annotation.WorkerThread;

import java.io.InputStream;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/25 14:53
 * @Dec 略
 */
public interface IFileConnect {

    /**
     * 获取文件的长度
     * @param path
     * @param callBack
     * @return
     */
    @WorkerThread
    long getConnectionLength(String path, IDownloadFileCallBack callBack);

    /**
     *
     * @param threadId
     * @param httpUrl
     * @param startIndex
     * @param endIndex
     * @param callBack
     * @return
     */
    @WorkerThread
    InputStream getInputStream(int threadId, String httpUrl,
                               long startIndex, long endIndex,
                               IFileDownloadCallback callBack);
}
