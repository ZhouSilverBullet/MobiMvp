package com.mobi.mvp;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/5/18 11:55
 * @Dec 略
 */
public interface IPresenter<V> {
    /**
     * activity 依附上的时候调用
     *
     * @param view
     */
    void onAttach(V view);

    /**
     * activity 销毁的时候调用
     */
    void onDetach();
}
