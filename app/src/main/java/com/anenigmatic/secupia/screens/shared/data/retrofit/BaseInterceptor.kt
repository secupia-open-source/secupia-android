package com.anenigmatic.secupia.screens.shared.data.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val modifiedRequest = chain!!.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(modifiedRequest)
    }
}