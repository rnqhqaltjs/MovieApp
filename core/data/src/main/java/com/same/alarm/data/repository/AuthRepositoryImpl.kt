package com.same.alarm.data.repository

import com.same.alarm.data.datasource.AuthDataSource
import com.same.alarm.data.datasource.LoginDataStore
import com.same.alarm.data.mapper.AuthMapper.toEntity
import com.same.alarm.data.mapper.AuthMapper.toModel
import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.model.LoginRequest
import com.same.alarm.model.LoginResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val loginDataStore: LoginDataStore,
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun saveAccessToken(token: String) {
        return loginDataStore.saveLoginToken(token)
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