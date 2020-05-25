package com.mobi.download;

import android.text.TextUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/25 14:52
 * @Dec 略
 */
public class DownloadApkManager {
    public static final int CURRENT_LENGTH = 4 * 1024;
    public static final int TIME_OUT = 8_000;

    private final String mFilePath;
    private String mRequestUrl;

    public DownloadApkManager(String requestUrl, String filePath) {
        if (TextUtils.isEmpty(requestUrl)) {
            requestUrl = "";
        }
        mRequestUrl = requestUrl;
        this.mFilePath = filePath;
    }

    private void connect() {
        URLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(mRequestUrl);
            connection = url.openConnection();
            connection.setConnectTimeout(TIME_OUT);

            if (isHttps()) {
                ((HttpsURLConnection) connection).setRequestMethod("GET");
            } else {
                ((HttpURLConnection) connection).setRequestMethod("GET");
            }

            //建立网络连接
            connection.connect();
            //创建写入的地址
            outputStream = new FileOutputStream(mFilePath);
            inputStream = connection.getInputStream();

            byte[] bytes = new byte[CURRENT_LENGTH];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            //链接格式不对
        } catch (IOException e) {
            e.printStackTrace();
            //openConnection 链接失败
        } finally {
            CloseUtil.close(inputStream);
            CloseUtil.close(outputStream);
        }

    }

    public boolean isHttps() {
        return mRequestUrl.startsWith("https");
    }
}
