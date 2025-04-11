package com.same.alarm.domain.repository

import com.same.alarm.model.LoginRequest
import com.same.alarm.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAccessToken(): Flow<String>
    suspend fun saveAccessToken(token: String)
    suspend fun login(kakaoAccessToken: String, loginRequest: LoginRequest): LoginResponse
}