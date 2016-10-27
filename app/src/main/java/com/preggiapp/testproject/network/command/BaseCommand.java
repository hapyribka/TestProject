package com.preggiapp.testproject.network.command;

import com.preggiapp.testproject.network.service.RetrofitService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseCommand {

    final static String SERVICE_URL = "http://www.mocky.io/";
    public static final String API_VERSION = "v2";

    public OkHttpClient.Builder httpClient;
    public Retrofit retrofit;
    public RetrofitService service;

    public BaseCommand() {
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
}
