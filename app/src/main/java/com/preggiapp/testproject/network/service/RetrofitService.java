package com.preggiapp.testproject.network.service;

import com.preggiapp.testproject.network.response.UsersListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("{id}/5762c6a8100000df1f8b14dd")
    Call<UsersListResponse> getUsers(@Path("id") String id);
}
