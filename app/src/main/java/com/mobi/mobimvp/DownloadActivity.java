package com.mobi.mobimvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.mobi.download.DownloadFileManager;
import com.mobi.download.IDownloadFileCallBack;

import java.io.File;

public class DownloadActivity extends AppCompatActivity {
    public static final String PATH = "https://fga1.market.xiaomi.com/download/AppStore/0140049b1a5a4494e6bcb744f74ddab8c0d417de2/com.sdxxtop.zhidian.apk";
    public static final String TAG = "DownloadActivity";

    private ProgressBar progressBar;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        progressBar = findViewById(R.id.pb);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
    }

    private void download() {
        File cacheDir = getExternalCacheDir();
        DownloadFileManager.download(PATH, cacheDir.getPath(), new IDownloadFileCallBack() {
            @Override
            public void onStart() {
                Log.e(TAG, "onStart : " );
            }

            @Override
            public void onUpdateProgress(long progress) {
                if (progress < 0) {
                    progress = 0L;
                }
                if (progress >= 100) {
                    progress = 100L;
                }
                progressBar.setProgress((int) progress);
                Log.e(TAG, "onUpdateProgress : " + progress);
            }

            @Override
            public void onFinished(String path) {
                Log.e(TAG, "onFinished : " + path);
            }

            @Override
            public void onError(String path, Exception e) {
                Log.e(TAG, e.getMessage());
            }
        });
    }
}
