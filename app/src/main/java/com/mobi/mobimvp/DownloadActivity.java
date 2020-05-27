package com.mobi.mobimvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.mobi.dialog.VersionUpdateDialog;
import com.mobi.download.DownloadFileManager;
import com.mobi.mobimvp.download.OkhttpConnect;

public class DownloadActivity extends AppCompatActivity {
    public static final String PATH = "https://dldir1.qq.com/weixin/android/weixin7014android1660.apk";
    public static final String TAG = "DownloadActivity";

    private ProgressBar progressBar;
    private Button btnStart;
    private int downloadId;
    private View btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        DownloadFileManager.getInstance().init(new OkhttpConnect());

        progressBar = findViewById(R.id.pb);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    private void download() {
        new VersionUpdateDialog.Builder(this)
                .setDownLoadUrl(PATH)
                .setVersionContent("更新测试")
                .setVersionName("1.max.1")
                .setIsForce(false)
                .build()
                .show();
//        File cacheDir = getExternalCacheDir();
//        DownloadFileManager.getInstance().download(PATH, cacheDir.getPath(), new IDownloadFileCallBack() {
//            @Override
//            public void onStart() {
//                Log.e(TAG, "onStart : " );
//            }
//
//            @Override
//            public void onUpdateProgress(long progress) {
////                if (progress < 0) {
////                    progress = 0L;
////                }
////                if (progress >= 100) {
////                    progress = 100L;
////                }
//                progressBar.setProgress((int) progress);
//                Log.e(TAG, "onUpdateProgress : " + progress);
//            }
//
//            @Override
//            public void onFinished(String path) {
//                Log.e(TAG, "onFinished : " + path);
//            }
//
//            @Override
//            public void onError(String path, Exception e) {
//                Log.e(TAG, e.getMessage());
//            }
//        });
    }
}
