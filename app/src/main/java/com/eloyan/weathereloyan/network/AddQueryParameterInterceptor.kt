package com.eloyan.weathereloyan.network

import com.eloyan.weathereloyan.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AddQueryParameterInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url().newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .addQueryParameter("aqi", "no")
            .addQueryParameter("alerts", "no")
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}