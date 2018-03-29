package com.test.hamsters.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.hamsters.network.response.BaseResponse;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

public abstract class AppObserver<T> implements Observer<T> {

    public abstract void onCompleted();

    public void onError(Throwable t) {
        ResponseBody body = ((HttpException) t).response().errorBody();
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            String errorBodyString = body.string();
            BaseResponse response = gson.fromJson(errorBodyString, BaseResponse.class);
            onErrorMessage(response.getMessage());
        } catch (Exception e) {
            t.printStackTrace();
            e.printStackTrace();
            onErrorMessage(e.getMessage());
        }
    }

    public abstract void onNext(T t);

    public abstract void onErrorMessage(String message);
}