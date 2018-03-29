package com.test.hamsters.network;

import android.util.Log;
import com.test.hamsters.AppApplication;
import com.test.hamsters.R;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NetworkProvider {

    private static NetworkProvider instance;
    private Api apiInterface;

    private NetworkProvider(String baseUrl) {
        Log.i("NetworkProvider", "new NetworkProvider. baseUrl - " + baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(initHttpClient())
                .build();
        apiInterface = retrofit.create(Api.class);
    }

    public static NetworkProvider getInstance(){
        if (instance == null) {
            String url = AppApplication.getInstance().getResources().getString(R.string.api_url);
            instance = new NetworkProvider(url);
        }
        return instance;
    }

    private OkHttpClient initHttpClient() {

        Interceptor loggingInterceptor = new  Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long t1 = System.nanoTime();
                String requestBodyString = null;
                if(request.body() != null) {
                    RequestBody requestBody = request.body();
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    requestBodyString = buffer.readString(Charset.forName("UTF-8"));
                }
                Log.i("OkHttp", "<--  Start request");
                Log.i("OkHttp", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
                Log.i("OkHttp", String.format("Sending body: %s", requestBodyString));
                Log.i("OkHttp", "End request  -->");
                Response response = chain.proceed(request);
                String responseBodyString = null;
                if(response.body() != null) {
                    ResponseBody responseBody = response.body();
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE);
                    Buffer buffer = source.buffer();
                    responseBodyString = buffer.clone().readString(Charset.forName("UTF-8"));
                }
                long t2 = System.nanoTime();
                Log.i("OkHttp", "Response code: "+response.code());
                Log.i("OkHttp", "<--  Start response");
                Log.i("OkHttp", String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                Log.i("OkHttp", String.format("Received body:  %s", responseBodyString));
                Log.i("OkHttp", "End response  -->");
                return response;
            }
        };



        OkHttpClient.Builder okHTTPBuilder = new OkHttpClient.Builder();
        return okHTTPBuilder
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    public Api getApiInterface() {
        return apiInterface;
    }
}