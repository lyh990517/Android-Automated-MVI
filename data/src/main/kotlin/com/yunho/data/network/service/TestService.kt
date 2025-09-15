package com.yunho.data.network.service

import com.yunho.data.network.dto.TestDto
import retrofit2.http.GET

interface TestService {
    @GET("/test")
    suspend fun getTest(): TestDto
}
