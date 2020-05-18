package com.mobi.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobi.dialog.LoadingDialog;
import com.mobi.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：CaiCM
 * 日期：2018/3/22  时间：15:24
 * 邮箱：15010104100@163.com
 * 描述：视图控制基类
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;
    protected LoadingDialog mLoadingDialog;
    protected AppCompatActivity mContext;
    protected View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getFragmentView(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView.getRootView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = (AppCompatActivity) getActivity();
        initVariables();
        initView();
        initData();
        initEvent();
    }

    protected void initVariables() {
    }

    public void showToast(String value) {
        ToastUtils.showLong(value);
    }

    /**
     * 初始化界面
     */
    protected abstract int getFragmentView();

    /**
     * 初始化数据
     */
    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 设置监听及回掉
     */
    protected void initEvent() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }

//        EventBus.getDefault().unregister(this);
    }

    protected void showProgressDialog() {
        showProgressDialog(false, null);
    }

    protected void showProgressDialog(boolean isCancel) {
        showProgressDialog(isCancel, null);
    }

    protected void showProgressDialog(boolean isCancel, String message) {
        if (null != mContext && !mContext.isFinishing()) {
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(getActivity());
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
