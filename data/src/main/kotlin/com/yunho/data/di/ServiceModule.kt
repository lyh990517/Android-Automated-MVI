package com.yunho.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yunho.data.BuildConfig
import com.yunho.data.network.interceptor.Interceptor
import com.yunho.data.network.service.TestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {
    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = when {
            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun providesFactory(interceptor: Interceptor): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(factory: Call.Factory): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .callFactory(factory)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Singleton
    @Provides
    fun providesTestService(retrofit: Retrofit): TestService =
        retrofit.create(TestService::class.java)
}
