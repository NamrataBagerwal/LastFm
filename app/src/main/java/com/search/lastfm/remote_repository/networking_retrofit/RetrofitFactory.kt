package com.search.lastfm.remote_repository.networking_retrofit

import com.search.lastfm.remote_repository.networking_retrofit.interceptor.CacheInterceptor
import com.search.lastfm.remote_repository.networking_retrofit.interceptor.OfflineCacheInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


object RetrofitFactory {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient().newBuilder()
        .addNetworkInterceptor(CacheInterceptor())
        .addInterceptor(OfflineCacheInterceptor())
        .addInterceptor(loggingInterceptor)
        .build()

    fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}