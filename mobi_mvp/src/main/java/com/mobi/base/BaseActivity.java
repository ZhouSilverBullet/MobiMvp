package com.mobi.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/18 14:54
 * @Dec 略
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    //butterKnife 绑定
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        bind = ButterKnife.bind(this);

        if (getIntent() != null) {
            initVariables(getIntent());
        }

        initView(savedInstanceState);
        initView();
        initEvent();
        initData();
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
     * @param intent
     */
    protected void initVariables(@NonNull Intent intent) {

    }

    protected void initView() {

    }

    /**
     * 一般的用不上
     * @param savedInstanceState
     */
    protected void initView(Bundle savedInstanceState) {

    }

    protected void initData() {

    }

    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
        bind = null;
    }
}