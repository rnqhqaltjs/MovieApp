package com.same.alarm.network.datasource

import com.same.alarm.data.datasource.LoginDataSource
import com.same.alarm.data.model.LoginEntity
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
): LoginDataSource {
    override suspend fun login(kakaoToken: String): LoginEntity {
        return LoginEntity(accessToken = "", refreshToken = "")
    }
}