package com.mobi.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobi.R;
import com.mobi.download.DownloadFileManager;
import com.mobi.download.FileUtil;
import com.mobi.download.IDownloadFileCallBack;
import com.mobi.util.ToastUtils;
import com.mobi.util.UiUtils;
import com.mobi.util.VersionUpdateUtil;

import java.io.File;

/**
 * author:zhaijinlu
 * date: 2019/11/12
 * desc:版本更新
 */
public class VersionUpdateDialog extends BaseDialog implements View.OnClickListener {
    public static final String TAG = "VersionUpdateDialog";

    private Context mContext;

    private TextView version_name;
    private LinearLayout layout_content;
    private RelativeLayout layout_update_force;
    private TextView once_update_force;//立即登录
    private LinearLayout layout_update;
    private TextView no_update;
    private TextView once_update;
    private LinearLayout layout_progress;
    private ProgressBar progress_bar;
    private TextView tv_progress;

    private boolean isForce;//是否强更
    private String versionName;//版本信息
    private String versionContent;//更新内容
    private String downLoadUrl;//下载链接


    public static String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/";
    public static String FILENAME = "mobi.apk";
    private File fileProgess;

    public VersionUpdateDialog(Builder builder) {
        super(builder.mContext);
        this.mContext = builder.mContext;
        this.isForce = builder.isForce;
        this.versionName = builder.versionName;
        this.versionContent = builder.versionContent;
        this.downLoadUrl = builder.downLoadUrl;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_version_layout);
        initView();
        initWindow();
    }

    private void initView() {
        version_name = findViewById(R.id.version_name);
        layout_content = findViewById(R.id.content_layout);
        layout_update_force = findViewById(R.id.layout_update_force);
        once_update_force = findViewById(R.id.once_update_force);
        layout_update = findViewById(R.id.layout_update);
        no_update = findViewById(R.id.no_update);
        once_update = findViewById(R.id.once_update);
        layout_progress = findViewById(R.id.layout_progress);
        progress_bar = findViewById(R.id.progress_bar);
        tv_progress = findViewById(R.id.tv_progress);

        if (!TextUtils.isEmpty(versionName)) {
            version_name.setText(mContext.getString(R.string.find_new, versionName));
        }
        if (!TextUtils.isEmpty(versionContent)) {
            String[] description = VersionUpdateUtil.splitPTag(versionContent);
            for (int i = 0; i < description.length; i++) {
                buildTextView(layout_content, description[i]);
            }
        }

        if (isForce) {//强更
            layout_update.setVisibility(View.GONE);
            layout_update_force.setVisibility(View.VISIBLE);
            layout_update_force.setOnClickListener(this);
        } else {
            layout_update.setVisibility(View.VISIBLE);
            layout_update_force.setVisibility(View.GONE);
            once_update.setOnClickListener(this);
            no_update.setOnClickListener(this);
        }


    }

    private void buildTextView(LinearLayout versionUpdateView, String textStr) {
        TextView tv = new TextView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, UiUtils.dp2px(mContext, 8), 0, 0);
        tv.setLayoutParams(layoutParams);
        tv.setText(textStr);
        tv.setTextColor(mContext.getResources().getColor(R.color.gray_7e9c));
        tv.setTextSize(16);
        versionUpdateView.addView(tv);
    }

    private void initWindow() {
        Window dialogWindow = this.getWindow();
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lps = dialogWindow.getAttributes();
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int w = dm.widthPixels > dm.heightPixels ? dm.heightPixels
                : dm.widthPixels;
        int h = dm.widthPixels > dm.heightPixels ? dm.widthPixels
                : dm.heightPixels;
        lps.width = w;
//        lps.height = h;
        lps.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lps);
        dialogWindow.setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.layout_update_force) {
            layout_update_force.setVisibility(View.GONE);
            layout_update.setVisibility(View.GONE);
            layout_progress.setVisibility(View.VISIBLE);
            downLoad();
        } else if (id == R.id.once_update) {
            layout_update_force.setVisibility(View.GONE);
            layout_update.setVisibility(View.GONE);
            layout_progress.setVisibility(View.VISIBLE);
            downLoad();
        } else if (id == R.id.no_update) {
            dismiss();
        }
    }

    private void downLoad() {
        fileProgess = new File(SDPATH);
        if (!fileProgess.exists()) {
            fileProgess.mkdirs();
        }
        DownloadFileManager.getInstance().setContext(getContext().getApplicationContext());
        DownloadFileManager.getInstance()
                .download(downLoadUrl, fileProgess.getPath(), new IDownloadFileCallBack() {
                    @Override
                    public void onStart() {
                        Log.e(TAG, "开始下载");
                    }

                    @Override
                    public void onUpdateProgress(long progress) {
                        Log.e(TAG, "开始下载 progress " + progress);
                        tv_progress.post(new Runnable() {
                            @Override
                            public void run() {
                                layout_progress.setVisibility(View.VISIBLE);
                                progress_bar.setVisibility(View.VISIBLE);
                                tv_progress.setVisibility(View.VISIBLE);
                                tv_progress.setText(mContext.getString(R.string.updating) + "(" + (int) progress + "%)");
                                progress_bar.setProgress((int) progress);
                            }
                        });
                    }

                    @Override
                    public void onFinished(String path) {
                        tv_progress.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                                File file = new File(fileProgess + "/" + FileUtil.getFileName(path));
                                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                }
                                installIntent.setDataAndType(getUriForFile(mContext, file), "application/vnd.android.package-archive");
                                mContext.startActivity(installIntent);
                            }
                        });

                    }

                    @Override
                    public void onError(String path, Exception e) {
                        Log.e(TAG, e.getMessage());
                        tv_progress.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showLong(e.getMessage());
                                if (isForce) {//强更
                                    layout_update.setVisibility(View.GONE);
                                    layout_update_force.setVisibility(View.VISIBLE);
                                    layout_update_force.setOnClickListener(VersionUpdateDialog.this);
                                } else {
                                    layout_update.setVisibility(View.VISIBLE);
                                    layout_update_force.setVisibility(View.GONE);
                                    once_update.setOnClickListener(VersionUpdateDialog.this);
                                    no_update.setOnClickListener(VersionUpdateDialog.this);
                                }
                                tv_progress.setVisibility(View.GONE);
                                progress_bar.setVisibility(View.GONE);
                            }
                        });

                    }
                });

    }


    //解决Android 7.0之后的Uri安全问题
    private Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            uri = FileProvider.getUriForFile(context, "com.mobi" + ".fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    @Override
    protected Context getActivityContext() {
        return mContext;
    }


    public static class Builder {
        private Context mContext;
        private boolean isForce;//是否强更
        private String versionName;//版本信息
        private String versionContent;//更新内容
        private String downLoadUrl;//下载链接

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setIsForce(boolean isForce) {
            this.isForce = isForce;
            return this;
        }

        public Builder setDownLoadUrl(String downLoadUrl) {
            this.downLoadUrl = downLoadUrl;
            return this;
        }

        public Builder setVersionName(String versionName) {
            this.versionName = versionName;
            return this;
        }

        public Builder setVersionContent(String versionContent) {
            this.versionContent = versionContent;
            return this;
        }


        public VersionUpdateDialog build() {
            return new VersionUpdateDialog(this);
        }
    }

}
