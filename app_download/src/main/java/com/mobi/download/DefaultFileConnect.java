package com.mobi.download;

import android.os.Build;

import com.mobi.download.exception.FileDownloadException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/26 14:22
 * @Dec 略
 */
public class DefaultFileConnect implements IFileConnect {

    @Override
    public long getConnectionLength(String path, IDownloadFileCallBack callBack) {
        //连接资源
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            int code = connection.getResponseCode();
            if (code == 200) {
                //获取资源大小
                long connectionLength;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    connectionLength = connection.getContentLengthLong();
                } else {
                    connectionLength = connection.getContentLength();
                }
                return connectionLength;
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (callBack != null) {
                callBack.onError(path, e);
            }
        }

        return 0L;
    }

    @Override
    public InputStream getInputStream(int threadId, String httpUrl, long startIndex, long endIndex, IFileDownloadCallback callBack) {
        //分段请求网络连接,分段将文件保存到本地.
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);

            //设置分段下载的头信息。  Range:做分段数据请求用的。格式: Range bytes=0-1024  或者 bytes:0-1024
            connection.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);

            System.out.println("DownloadThread 线程_" + threadId + "的下载起点是 " + startIndex + "  下载终点是: " + endIndex);

            //200：请求全部资源成功， 206代表部分资源请求成功
            if (connection.getResponseCode() == 206) {
                //获取流
                return connection.getInputStream();
            } else {
                System.out.println("DownloadThread 响应码是" + connection.getResponseCode() + ". 服务器不支持多线程下载");
                if (callBack != null) {
                    callBack.onError(new FileDownloadException("响应码是" + connection.getResponseCode() + ". 服务器不支持多线程下载"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (callBack != null) {
                callBack.onError(e);
            }
        }

        return null;
    }
}
