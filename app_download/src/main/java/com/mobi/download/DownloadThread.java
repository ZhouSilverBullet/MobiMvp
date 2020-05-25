package com.mobi.download;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Email: zhousaito@163.com
 * Created by zhousaito 2020/5/25 17:59
 * Version: 1.0
 * Description:
 */
public class DownloadThread extends Thread {

    private final String httpUrl;
    private final String targetFilePath;
    private int threadId;
    private long startIndex;
    private long endIndex;
    private IDownloadFileCallBack callBack;
    private long currentThreadTotal;//当前线程下载文件的总大小
    private File downThreadFile;

    /**
     * @param threadId   第几个线程 从0开始
     * @param startIndex 下载开始的位置
     * @param endIndex   下载结束的位置
     * @param httpUrl    下载的http地址
     * @param targetPath 下载到目标的路径
     * @param callBack   回调callBack
     */
    public DownloadThread(int threadId,
                          long startIndex,
                          long endIndex,
                          String httpUrl,
                          String targetPath,
                          IDownloadFileCallBack callBack) {
        this.threadId = threadId;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.httpUrl = httpUrl;
        this.targetFilePath = targetPath;
        this.currentThreadTotal = endIndex - startIndex + 1;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        System.out.println("线程" + threadId + "开始下载");
        try {
            //分段请求网络连接,分段将文件保存到本地.
            URL url = new URL(httpUrl);

            //加载下载位置的文件
            downThreadFile = new File(targetFilePath, FileUtil.getFileName(url) + "_downThread_" + threadId + ".dt");
            RandomAccessFile downThreadStream = null;
            //如果文件存在
            if (downThreadFile.exists()) {
                downThreadStream = new RandomAccessFile(downThreadFile, "rwd");
                String startIndexStr = downThreadStream.readLine();

                long lastStartIndex = Long.parseLong(startIndexStr);//设置下载起点
                //获取上次的进度
                long lastTotal = lastStartIndex - startIndex;
                //获取到进度条直接往外面传
                if (callBack != null) {
                    callBack.onUpdateProgress(lastTotal);
                }
                //开始节点
                this.startIndex = lastStartIndex;
            } else {
                downThreadStream = new RandomAccessFile(downThreadFile, "rwd");
            }

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);

            //证明已经下载完成
            if (startIndex >= endIndex) {
                if (callBack != null) {
                    callBack.onFinished(null);
                }
                return;
            }

            //设置分段下载的头信息。  Range:做分段数据请求用的。格式: Range bytes=0-1024  或者 bytes:0-1024
            connection.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);

            System.out.println("线程_" + threadId + "的下载起点是 " + startIndex + "  下载终点是: " + endIndex);

            //200：请求全部资源成功， 206代表部分资源请求成功
            if (connection.getResponseCode() == 206) {
                //获取流
                InputStream inputStream = connection.getInputStream();
                //获取前面已创建的文件.
                RandomAccessFile randomAccessFile = new RandomAccessFile(
                        new File(targetFilePath, FileUtil.getFileName(url)), "rw");
                //文件写入的开始位置.
                randomAccessFile.seek(startIndex);

                //将网络流中的文件写入本地
                byte[] buffer = new byte[1024 * 1024];
                int length = -1;
                //记录本次下载文件的大小
                long total = 0L;

                while ((length = inputStream.read(buffer)) > 0) {
                    randomAccessFile.write(buffer, 0, length);
                    total += length;
                    if (callBack != null) {
                        callBack.onUpdateProgress(length);
                    }
                    /**
                     * 将当前现在到的位置保存到文件中
                     */
                    long currentThreadPosition = startIndex + total;
                    downThreadStream.seek(0);
                    downThreadStream.write((currentThreadPosition + "").getBytes("UTF-8"));
                }
                downThreadStream.close();
                inputStream.close();
                randomAccessFile.close();
                //删除临时文件
//                cleanTemp(downThreadFile);
                System.out.println("线程" + threadId + "下载完毕");

                if (callBack != null) {
                    callBack.onFinished(null);
                }
            } else {
                System.out.println("响应码是" + connection.getResponseCode() + ". 服务器不支持多线程下载");
                if (callBack != null) {
                    IllegalAccessException e = new IllegalAccessException("响应码是" +
                            connection.getResponseCode() + ". 服务器不支持多线程下载");
                    callBack.onError(null, e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.onError(null, e);
            }
        }

    }

    //删除线程产生的临时文件
    private synchronized void cleanTemp(File file) {
        file.delete();
    }

    /**
     * 删除文件
     */
    public void deleteTemp() {
        if (downThreadFile != null && downThreadFile.exists()) {
            cleanTemp(downThreadFile);
        }
    }

    /**
     * 在http的情况下，照样可以进行，可能是因为不需要
     * 有https验证证书的原因吧
     * HttpURLConnection 兼容上了 HttpsURLConnection
     *
     * @return
     */
    public boolean isHttps() {
        return httpUrl.startsWith("https");
    }

}

