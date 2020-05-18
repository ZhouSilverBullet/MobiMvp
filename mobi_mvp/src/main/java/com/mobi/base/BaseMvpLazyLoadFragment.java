package com.mobi.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobi.mvp.BasePresenter;


/**
 * Fragment预加载问题的解决方案：
 * 1.可以懒加载的Fragment
 * 2.切换到其他页面时停止加载数据（可选）
 * Created by yuandl on 2016-11-17.
 * blog ：http://blog.csdn.net/linglongxin24/article/details/53205878
 */

public abstract class BaseMvpLazyLoadFragment<P extends BasePresenter> extends BaseMvpFragment<P> {

    private static final String TAG = "LazyLoadFragment";

    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;
    protected boolean isFirstIn = true;//是否首次进入当前页面 只有首次进入才加载数据

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        /**初始化的时候去加载数据**/
        isInit = true;
        isCanLoadData();
        return view;
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint()) {
            isLoad = true;
            if (isFirstIn) {
                firstIn();
                isFirstIn = false;
            }
            lazyLoad();
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
        isFirstIn = true;

    }

    /**
     * 获取设置的布局
     *
     * @return
     */
    protected View getRootView() {
        return mRootView;
    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 首次进入当前界面
     */
    protected abstract void firstIn();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以调用此方法
     */
    protected void stopLoad() {
    }
}