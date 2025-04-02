package com.same.alarm.network.remote

import com.same.alarm.data.model.LoginEntity

interface AuthService {
    suspend fun login(kakaoToken: String): LoginEntity
}