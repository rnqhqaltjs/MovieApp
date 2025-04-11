package com.same.alarm.data.repository

import com.same.alarm.data.datasource.AuthDataSource
import com.same.alarm.data.datasource.TokenStorage
import com.same.alarm.data.mapper.AuthMapper.toEntity
import com.same.alarm.data.mapper.AuthMapper.toModel
import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.model.LoginRequest
import com.same.alarm.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override fun getAccessToken(): Flow<String> = tokenStorage.getAccessToken()

    override suspend fun saveAccessToken(token: String) {
        return tokenStorage.saveLoginToken(token)
    }

    override suspend fun login(
        kakaoAccessToken: String,
        loginRequest: LoginRequest
    ): LoginResponse {
        val loginRequestEntity = loginRequest.toEntity()
        val response = authDataSource.login(
            kakaoAccessToken = "Bearer $kakaoAccessToken",
            loginRequestEntity = loginRequestEntity
        )

        return response.toModel()
    }
}