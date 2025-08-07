package com.same.alarm.network.interceptor

import com.same.alarm.data.datasource.TokenStorage
import com.same.alarm.network.remote.AuthService
import dagger.Lazy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authService: Lazy<AuthService>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code != HTTP_UNAUTHORIZED) {
            return null
        }

        val refreshToken = runBlocking {
            tokenStorage.getRefreshToken().first()
        }

        val reissueResult = runCatching {
            runBlocking {
                authService.get().reissue(refreshToken).body
            }
        }

        val (newAccessToken, newRefreshToken) = reissueResult.getOrNull()?.let { it.accessToken to it.refreshToken } ?: return null

        runBlocking {
            tokenStorage.saveAccessToken(BEARER + newAccessToken)
            tokenStorage.saveRefreshToken(BEARER + newRefreshToken)
        }

        return response.request.newBuilder()
            .header(AUTHORIZATION, BEARER + newAccessToken)
            .build()
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer "
    }
}