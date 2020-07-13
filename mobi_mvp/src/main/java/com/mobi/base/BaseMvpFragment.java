package com.mobi.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mobi.mvp.BasePresenter;
import com.mobi.mvp.IView;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/18 17:45
 * @Dec 略
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements IView {
    @Nullable
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.onAttach(this);
            //默认把presenter 中的生命周期注册感知上
            getLifecycle().addObserver(mPresenter);
        }

        super.onCreate(savedInstanceState);
    }

    /**
     * 外部实现presenter
     *
     * @return
     */
    protected abstract P getPresenter();


    @Nullable
    @Override
    public Activity getMvpActivity() {
        if (mContext instanceof Activity) {
            return ((Activity) mContext);
        }
        return getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放presenter
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
        mPresenter = null;
    }
}
