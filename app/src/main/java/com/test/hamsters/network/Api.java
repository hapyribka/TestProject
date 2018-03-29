package com.test.hamsters.network;

import com.test.hamsters.models.Hamster;
import java.util.ArrayList;
import retrofit2.http.GET;
import rx.Observable;

public interface Api {

    @GET("test3")
    Observable<ArrayList<Hamster>> getHamsters();
}