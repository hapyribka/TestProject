package com.test.points.repositories.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.points.repositories.network.response.PointsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkApiImpl : NetworkApi {
    private val baseUrl = "https://hr-challenge.dev.tapyou.com"
    private val apiBuild: Retrofit
    private val api: Api

    init {
        val gson: Gson =
            GsonBuilder()
                .setLenient()
                .create()

        apiBuild =
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(createOkHttpClient())
                .build()

        api = apiBuild.create(Api::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpClientBuilder: OkHttpClient.Builder =
            OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClientBuilder.build()
    }

    override suspend fun getPoints(count: Int): PointsResponse? {
        return api.getPoints(count)
    }
}
