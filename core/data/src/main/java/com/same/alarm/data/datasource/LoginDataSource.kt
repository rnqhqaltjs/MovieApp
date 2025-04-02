package com.same.alarm.data.datasource

import com.same.alarm.data.model.LoginEntity

interface LoginDataSource {
    suspend fun login(kakaoToken: String): LoginEntity
}