package com.same.alarm.domain.repository

import com.same.alarm.model.login.LoginRequest
import com.same.alarm.model.login.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAccessToken(): Flow<String>
    suspend fun saveAccessToken(token: String)
    fun getRefreshToken(): Flow<String>
    suspend fun saveRefreshToken(token: String)
    suspend fun login(kakaoAccessToken: String, loginRequest: LoginRequest): LoginResponse
    suspend fun logout()
}