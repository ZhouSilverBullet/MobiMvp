package com.mobi.mobimvp.download;

import android.support.annotation.WorkerThread;

import com.mobi.download.IDownloadFileCallBack;
import com.mobi.download.IFileConnect;
import com.mobi.download.IFileDownloadCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/26 14:35
 * @Dec 略
 */
public class OkhttpConnect implements IFileConnect {
    private OkHttpClient client;

    public OkhttpConnect() {
        client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @WorkerThread
    @Override
    public long getConnectionLength(String path, IDownloadFileCallBack callBack) {
        Request request = new Request.Builder()
                .get()
                .url(path)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().contentLength();
        } catch (IOException e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.onError(path, e);
            }
        }
        return 0L;
    }

    @WorkerThread
    @Override
    public InputStream getInputStream(int threadId, String httpUrl, long startIndex, long endIndex, IFileDownloadCallback callBack) {
        Request request = new Request.Builder()
                .get()
                .url(httpUrl)
                .addHeader("Range", "bytes=" + startIndex + "-" + endIndex)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 206) {
                return response.body().byteStream();
            }
        } catch (IOException e) {
            e.printStackTrace();

            if (callBack != null) {
                callBack.onError(e);
            }
        }
        return null;
    }
}
