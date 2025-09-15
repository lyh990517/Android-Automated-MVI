package com.yunho.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class Interceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val builder = request().newBuilder()

        builder
//            .addHeader("Authorization", "")
            .build()
            .let(::proceed)
    }
}
