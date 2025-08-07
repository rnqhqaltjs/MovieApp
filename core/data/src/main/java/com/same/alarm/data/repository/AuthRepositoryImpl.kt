package com.same.alarm.data.repository

import com.same.alarm.data.datasource.AuthDataSource
import com.same.alarm.data.datasource.TokenStorage
import com.same.alarm.data.mapper.AuthMapper.toEntity
import com.same.alarm.data.mapper.AuthMapper.toDomain
import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.model.login.LoginRequest
import com.same.alarm.model.login.LoginResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override fun getAccessToken(): Flow<String> = tokenStorage.getAccessToken()

    override suspend fun saveAccessToken(token: String) {
        return tokenStorage.saveAccessToken(BEARER + token)
    }

    override fun getRefreshToken(): Flow<String> = tokenStorage.getRefreshToken()

    override suspend fun saveRefreshToken(token: String) {
        return tokenStorage.saveRefreshToken(BEARER + token)
    }

    override suspend fun login(
        kakaoAccessToken: String,
        loginRequest: LoginRequest
    ): LoginResponse {
        val loginRequestEntity = loginRequest.toEntity()
        val response = authDataSource.login(
            kakaoAccessToken = BEARER + kakaoAccessToken,
            loginRequestEntity = loginRequestEntity
        )

        return response.toDomain()
    }

    override suspend fun logout() {
        return authDataSource.logout()
    }

    companion object {
        const val BEARER = "Bearer "
    }
}