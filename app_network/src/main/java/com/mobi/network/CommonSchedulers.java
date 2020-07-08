package com.mobi.network;

import android.content.Context;

import com.mobi.network.callback.IRequestCallback;
import com.mobi.network.exception.ApiException;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CommonSchedulers {

    public static <D> Disposable execHttp(Observable<D> observable, IRequestCallback<D> requestCallback) {
        return observable
                .compose(observableIO2Main())
                .subscribe(successData(requestCallback),  //成功
                        throwableConsumer(requestCallback)); //失败
    }

    private static <D> Consumer<D> successData(final IRequestCallback<D> requestCallback) {
        return new Consumer<D>() {
            @Override
            public void accept(D d) throws Exception {
                if (requestCallback != null) {
                    if (d != null) {
                        requestCallback.onSuccess(d);
                    } else {
                        requestCallback.onFailure(-100, "数据源为空");
                    }
                }
            }
        };
    }

    private static Consumer<Throwable> throwableConsumer(final IRequestCallback requestCallback) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (requestCallback != null) {
                    if (throwable instanceof ApiException) {
                        requestCallback.onFailure(((ApiException) throwable).getCode(), throwable.getMessage());
                    } else {
                        requestCallback.onFailure(-100, RxExceptionUtil.exceptionHandler(throwable));
                    }
                }
            }
        };
    }

    public static <T> ObservableTransformer<T, T> observableIO2Main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }



    public static <T> ObservableTransformer<T, T> observableIO2Main(final Context context) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                return composeContext(context, observable);
            }
        };
    }

    private static <T> ObservableSource<T> composeContext(Context context, Observable<T> observable) {
        if (context instanceof RxFragmentActivity) {
            return (ObservableSource<T>) observable.compose(((RxFragmentActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
        } else if (context instanceof RxAppCompatActivity) {
            return (ObservableSource<T>) observable.compose(((RxAppCompatActivity) context).bindUntilEvent(ActivityEvent.DESTROY));
        } else {
            return observable;
        }
    }


}
