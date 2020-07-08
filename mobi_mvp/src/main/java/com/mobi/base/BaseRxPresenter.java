package com.mobi.base;

import com.mobi.mvp.BasePresenter;
import com.mobi.mvp.IView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/28 20:34
 * @Dec 略
 */
public class BaseRxPresenter<V extends IView> extends BasePresenter<V> {
    CompositeDisposable mCompositeDisposable;

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unSubscribe();
    }
}
