package com.cristo.agenda.data.remote

import com.cristo.agenda.data.local.SessionStorage
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val session: SessionStorage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = session.token()
        val request = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}