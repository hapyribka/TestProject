package com.test.points.repositories.network

import com.test.points.repositories.network.response.PointsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/api/test/points")
    suspend fun getPoints(
        @Query("count") count: Int
    ): PointsResponse?
}
