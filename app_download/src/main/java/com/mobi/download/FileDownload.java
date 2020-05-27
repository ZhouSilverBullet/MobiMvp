package com.mobi.download;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FileDownload implements Runnable, IFileDownloadCallback {

    private String path = "https://fga1.market.xiaomi.com/download/AppStore/0140049b1a5a4494e6bcb744f74ddab8c0d417de2/com.sdxxtop.zhidian.apk";
    private String targetFilePath = "apk2/";  //下载文件存放目录
    private int threadCount;   //线程数量
    private IDownloadFileCallBack callBack;
    //记录总的长度，用来算比例
    private final AtomicLong progressAtc;
    //用来计算线程未完成的数量
    private final AtomicInteger threadRunningAtc;
    private long connectionLength;

    /**
     * 用来存DownloadThread，然后等全部下载完成后，
     * 清理temp文件
     */
    private List<DownloadThread> downloadThreadList;
    private IFileConnect fileConnect;

    /**
     * @param path           要下载文件的网络路径
     * @param targetFilePath 保存下载文件的目录
     * @param threadCount    开启的线程数量,默认为 3
     */
    public FileDownload(String path, String targetFilePath, int threadCount, IFileConnect connect) {
        this.path = path;
        this.targetFilePath = targetFilePath;
        this.threadCount = threadCount;

        progressAtc = new AtomicLong(0);
        threadRunningAtc = new AtomicInteger(threadCount);
        downloadThreadList = new ArrayList<>(threadCount);
        //如果是null，就用默认的请求方式
        this.fileConnect = connect == null ? new DefaultFileConnect() : connect;
    }

    public FileDownload(String path, String targetFilePath) {
        this(path, targetFilePath, 3, new DefaultFileConnect());
    }

    public void setCallBack(IDownloadFileCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 下载文件
     */
    private void download() throws Exception {
        //开始
        onStart();

        connectionLength = fileConnect.getConnectionLength(path, callBack);

        System.out.println(connectionLength);

        //长度为0的时候，非法的长度
        if (connectionLength == 0) {
            if (callBack != null) {
                IllegalStateException zero = new IllegalStateException("connect length by zero");
                callBack.onError(path, zero);
            }
            return;
        }

        //是同一个文件
        if (checkFile(connectionLength)) {
            if (callBack != null) {
                callBack.onUpdateProgress(100L);
                callBack.onFinished(path);
            }
            return;
        }


        //在本地创建一个与资源同样大小的文件来占位
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(targetFilePath, FileUtil.getFileName(path)), "rw");
        randomAccessFile.setLength(connectionLength);

        //计算每个线程理论上下载的数量.
        int blockSize = (int) (connectionLength / threadCount);
        //为每个线程分配任务
        //资源的总长度 为 0 - connectionLength - 1，计算机从0开始的，所以
        for (int threadId = 0; threadId < threadCount; threadId++) {
            //线程开始下载的位置
            long startIndex = threadId * blockSize;
            //线程结束下载的位置
            long endIndex = (threadId + 1) * blockSize - 1;
            //如果是最后一个线程,将剩下的文件全部交给这个线程完成
            if (threadId == (threadCount - 1)) {
                endIndex = connectionLength - 1;
            }
            //开启线程下载
            DownloadThread downloadThread = new DownloadThread(threadId, startIndex, endIndex,
                    path, targetFilePath, this);
            downloadThread.setConnect(fileConnect);
            downloadThread.start();
            downloadThreadList.add(downloadThread);
        }
        CloseUtil.close(randomAccessFile);
    }

    private boolean checkFile(long contentLength) {
        File file = new File(targetFilePath + "/" + FileUtil.getFileName(path));
        if (file.exists()) {
            if (file.length() == contentLength) {
                boolean unintallApkInfo = getUnintallApkInfo(DownloadFileManager.getInstance().getContext(), file.getPath());
                return unintallApkInfo;
            }
        }
        return false;
    }

    public boolean getUnintallApkInfo(Context context, String filePath) {
        if (context == null) {
            return false;
        }
        boolean result = false;
        try {
            PackageManager pm = context.getPackageManager();
            Log.e("archiveFilePath", filePath);
            PackageInfo info = pm.getPackageArchiveInfo(filePath,PackageManager.GET_ACTIVITIES);
            if (info != null) {
                result = true;//完整
            }
        } catch (Exception e) {
            result = false;//不完整
        }
        return result;
    }

    public long getConnectionLength(String path) {
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0L;
    }


//    public static void main(String[] args) {
//        try {
//            final FileDownload apk2 = new FileDownload("https://fga1.market.xiaomi.com/download/AppStore/0140049b1a5a4494e6bcb744f74ddab8c0d417de2/com.sdxxtop.zhidian.apk",
//                    "apk2");
//            apk2.setCallBack(new IDownloadFileCallBack() {
//                @Override
//                public void onStart() {
//                    System.err.println("onStart");
//                }
//
//                @Override
//                public void onUpdateProgress(long progress) {
//                    System.err.println("progress : " + progress + "%");
//                }
//
//                @Override
//                public void onFinished(String path) {
//                    System.err.println("onFinished path = " + path);
//                }
//
//                @Override
//                public void onError(String path, Exception e) {
//                    System.err.println("onFinished e = " + e);
//                }
//            });
//            apk2.download();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 告知外面启动开始
     */
    private void onStart() {
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onUpdateProgress(long progress) {
        if (connectionLength == 0) {
            connectionLength = 1L;
        }
        long tempProgress = progressAtc.addAndGet(progress);
        System.out.println("DownloadThread onUpdateProgress : " + connectionLength + " tempProgress : " + tempProgress);
        tempProgress = tempProgress * 100 / connectionLength;
        if (callBack != null) {
            callBack.onUpdateProgress(tempProgress);
        }
    }

    @Override
    public void onFinished() {
        int running = threadRunningAtc.decrementAndGet();
        if (running <= 0 && callBack != null) {
            callBack.onFinished(this.path);
            //全部完成后，循环删除缓存文件
            for (DownloadThread downloadThread : downloadThreadList) {
                downloadThread.deleteTemp();
            }
        }
    }

    @Override
    public void onError(Exception e) {
        if (callBack != null) {
            callBack.onError(this.path, e);
        }
    }

    @Override
    public void run() {
        try {
            download();
        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.onError(path, e);
            }
        }
    }

    public void pause() {
        if (downloadThreadList != null) {
            for (DownloadThread downloadThread : downloadThreadList) {
                downloadThread.pause();
            }
        }
    }
}

