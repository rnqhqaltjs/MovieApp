package com.same.alarm.network.interceptor

import com.same.alarm.data.datasource.TokenStorage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenStorage: TokenStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val excludedPaths = listOf(
            "/auth/login",
            "/auth/reissue"
        )

        if (originalRequest.url.encodedPath in excludedPaths) {
            return chain.proceed(originalRequest)
        }

        val accessToken = runBlocking { tokenStorage.getAccessToken().first() }
        val isLoggedIn = runBlocking { tokenStorage.isLoggedIn() }

        val authRequest =
            if (isLoggedIn) originalRequest.newAuthBuilder(accessToken) else originalRequest
        val response = chain.proceed(authRequest)

        return response
    }

    private fun Request.newAuthBuilder(accessToken: String) =
        this.newBuilder()
            .addHeader(AUTHORIZATION, accessToken)
            .build()

    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}