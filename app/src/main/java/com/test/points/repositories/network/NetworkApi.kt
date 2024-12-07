package com.test.points.repositories.network

import com.test.points.repositories.network.response.PointsResponse

interface NetworkApi {
    suspend fun getPoints(count: Int): PointsResponse?
}
