package com.preggiapp.testproject.network.command;

import com.preggiapp.testproject.network.callbacks.FragmentCallBack;
import com.preggiapp.testproject.network.response.BaseResponse;
import com.preggiapp.testproject.network.service.RetrofitService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseCommand {

    final static String SERVICE_URL = "http://www.mocky.io/";
    public FragmentCallBack callback;
    public OkHttpClient.Builder httpClient;
    public Retrofit retrofit;
    public RetrofitService service;

    public BaseCommand(FragmentCallBack callback) {
        this.callback = callback;
        initRetrofit();
    }

    public void initRetrofit() {
        okhttp3.logging.HttpLoggingInterceptor logging = new okhttp3.logging.HttpLoggingInterceptor();
        logging.setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().add(logging);

        retrofit = new Retrofit.Builder()
                .baseUrl(SERVICE_URL)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RetrofitService.class);
    }

    public boolean checkErrors(BaseResponse resp) {
        if(resp.getError() != null) {
            if(callback != null) {
                callback.onLoadFinished(resp.getError());
            }
            return true;
        }
        return false;
    }

    public abstract void execute();
}
