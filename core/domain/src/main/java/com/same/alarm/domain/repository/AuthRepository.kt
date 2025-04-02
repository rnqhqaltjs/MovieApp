package com.same.alarm.domain.repository

import com.same.alarm.model.Login

interface AuthRepository {
    suspend fun saveAccessToken(token: String)
    suspend fun login(kakaoToken: String): Login
}