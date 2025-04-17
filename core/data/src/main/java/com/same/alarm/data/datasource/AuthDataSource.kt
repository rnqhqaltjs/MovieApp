package com.same.alarm.data.datasource

import com.same.alarm.data.model.login.LoginRequestEntity
import com.same.alarm.data.model.login.LoginResponseEntity

interface AuthDataSource {
    suspend fun login(
        kakaoAccessToken: String,
        loginRequestEntity: LoginRequestEntity
    ): LoginResponseEntity

    suspend fun logout()
}