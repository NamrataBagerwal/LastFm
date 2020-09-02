package com.search.lastfm.remote_repository.networking_retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)
        val cacheControl = originalResponse.header("Cache-Control")

        return if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
            cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
        ) {
            originalResponse.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public, max-age=" + 5000)
                .build()

        } else {
            originalResponse
        }
    }
}
