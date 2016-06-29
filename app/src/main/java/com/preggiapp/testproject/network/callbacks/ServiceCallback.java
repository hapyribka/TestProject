package com.preggiapp.testproject.network.callbacks;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ServiceCallback<T> implements Callback<T> {

    public void onResponse(Call<T> call, Response<T> response) {
        Log.i("AppCallback", "onResponse - "+response);
        onResponse(response, null);
    }

    public void onFailure(Call<T> call, Throwable t) {
        Log.i("AppCallback", "onFailure - "+t.toString());
        onResponse(null, t.getMessage());
    }

    public abstract void onResponse(Response<T> response, String error);
}
