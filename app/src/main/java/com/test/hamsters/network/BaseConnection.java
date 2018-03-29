package com.test.hamsters.network;

import android.os.AsyncTask;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BaseConnection {

    protected Api apiInterface;

    public BaseConnection() {
        apiInterface = NetworkProvider.getInstance().getApiInterface();
    }

    protected  <T> Observable<T> setThreads(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .retry(2);
    }
}