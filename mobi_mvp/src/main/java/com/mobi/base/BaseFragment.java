package com.mobi.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.mobi.dialog.LoadingDialog;
import com.mobi.feature.ILoadSirDelegate;
import com.mobi.loadsir.LoaderSirUtil;
import com.mobi.permission.RxPermissions;
import com.mobi.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：CaiCM
 * 日期：2018/3/22  时间：15:24
 * 邮箱：15010104100@163.com
 * 描述：视图控制基类
 */

public abstract class BaseFragment extends Fragment implements ILoadSirDelegate {

    private Unbinder mUnbinder;
    protected LoadingDialog mLoadingDialog;
    protected View mRootView;
    protected Context mContext;
    private RxPermissions mRxPermissions;
    private LoadService mLoadService;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = loadSirBindView();
        mRootView = inflater.inflate(getFragmentView(), container, false);
        if (view == null) {
            mLoadService = LoadSir.getDefault().register(mRootView.getRootView(), new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    preLoad();
                }
            });
        } else {
            //view不为空表示子类想自己去实现LoadSir,一般出现在有title的情况
            //当有title的情况，方便子类 自己显现一个title类型的loadSir
            mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    preLoad();
                }
            });
        }
        mUnbinder = ButterKnife.bind(this, mRootView);
        return mRootView.getRootView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    public LoadService getLoadService() {
        return mLoadService;
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        mUnbinder = null;

//        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取权限
     *
     * @return
     */
    public RxPermissions getRxPermissions() {
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(this);
        }
        return mRxPermissions;
    }

    /**
     * 判断fragment 是否添加在了activity上面
     * @return
     */
    public boolean isCanUseFragment() {
        return mContext instanceof Activity && isAdded();
    }

    protected void showProgressDialog() {
        showProgressDialog(false, null);
    }

    protected void showProgressDialog(boolean isCancel) {
        showProgressDialog(isCancel, null);
    }

    protected void showProgressDialog(boolean isCancel, String message) {
        if (mContext != null) {
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
