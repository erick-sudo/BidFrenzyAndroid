package com.draw.bidfrenzy.repository

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class PathInterceptor(
    private val newPathSegments: List<String>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val newUrlBuilder: HttpUrl.Builder = originalRequest.url.newBuilder()

        newPathSegments.forEach { pathSegment ->
            newUrlBuilder.addPathSegment(pathSegment)
        }

        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrlBuilder.build())
            .build()

        return chain.proceed(newRequest)
    }
}