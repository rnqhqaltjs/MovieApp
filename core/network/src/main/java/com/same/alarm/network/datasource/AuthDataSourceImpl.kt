package com.same.alarm.network.datasource

import com.same.alarm.data.datasource.AuthDataSource
import com.same.alarm.data.model.LoginRequestEntity
import com.same.alarm.data.model.LoginResponseEntity
import com.same.alarm.network.mapper.AuthMapper.toDto
import com.same.alarm.network.mapper.AuthMapper.toEntity
import com.same.alarm.network.remote.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthDataSource {
    override suspend fun login(
        kakaoAccessToken: String,
        loginRequestEntity: LoginRequestEntity
    ): LoginResponseEntity {
        val response = authService.login(
            accessToken = kakaoAccessToken,
            loginRequestDto = loginRequestEntity.toDto()
        )
        return response.toEntity()
    }

    override suspend fun logout() {
        return authService.logout()
    }
}