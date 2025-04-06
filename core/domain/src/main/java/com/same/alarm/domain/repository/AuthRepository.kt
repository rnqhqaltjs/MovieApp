package com.same.alarm.domain.repository

import com.same.alarm.model.LoginRequest
import com.same.alarm.model.LoginResponse

interface AuthRepository {
    suspend fun saveAccessToken(token: String)
    suspend fun login(kakaoAccessToken: String, loginRequest: LoginRequest): LoginResponse
}