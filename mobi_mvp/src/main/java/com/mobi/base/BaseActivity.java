package com.mobi.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.mobi.dialog.LoadingDialog;
import com.mobi.feature.ILoadSirDelegate;
import com.mobi.permission.RxPermissions;
import com.mobi.util.StatusBarPaddingUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/18 14:54
 * @Dec 略
 */
public abstract class BaseActivity extends AppCompatActivity implements ILoadSirDelegate {
    //butterKnife 绑定
    private Unbinder bind;
    private LoadingDialog mLoadingDialog;
    private RxPermissions mRxPermissions;
    private LoadService mLoadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFitWindow()) {
            fitWindow();
        }

        setContentView(getLayoutId());

        //设置状态栏的颜色
        setDarkStatusIcon(false);

        bind = ButterKnife.bind(this);

        loadSir();

        if (getIntent() != null) {
            initVariables(getIntent());
        }

        initView();
        initView(savedInstanceState);
        initEvent();
        initData();
    }

    private void loadSir() {
        mLoadService = LoadSir.getDefault().register(this, (Callback.OnReloadListener) v -> {
            // 重新加载逻辑
            preLoad();
        });
    }

    public LoadService getLoadService() {
        return mLoadService;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            initVariables(intent);
        }
    }

    /**
     * 布局文件id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 处理传过来的intent数据
     *
     * @param intent
     */
    protected void initVariables(@NonNull Intent intent) {

    }

    protected void initView() {

    }

    /**
     * 一般的用不上
     *
     * @param savedInstanceState
     */
    protected void initView(Bundle savedInstanceState) {

    }

    protected void initData() {

    }

    protected void initEvent() {

    }

    @Override
    public void preLoad() {

    }

    @Nullable
    @Override
    public View loadSirBindView() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
        bind = null;
    }

    /**
     * 默认不打开
     *
     * @return
     */
    protected boolean isFitWindow() {
        return false;
    }

    private void fitWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 获取权限申请
     * @return
     */
    public RxPermissions getRxPermissions() {
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(this);
        }
        return mRxPermissions;
    }

    public void setDarkStatusIcon(boolean bDark) {
        StatusBarPaddingUtil.statusBar(this, bDark);
    }

    protected void showProgressDialog() {
        showProgressDialog(true, null);
    }

    protected void showProgressDialog(boolean isCancel) {
        showProgressDialog(isCancel, null);
    }

    protected void showProgressDialog(boolean isCancel, String message) {
        if (!isFinishing()) {
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(this);
            }
            mLoadingDialog.setCancelable(isCancel);
            mLoadingDialog.show();
            mLoadingDialog.setMessage(message);
        }
    }

    protected void closeProgressDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
