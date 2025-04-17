package com.same.alarm.data.datasource

import kotlinx.coroutines.flow.Flow

interface TokenStorage {
    fun getAccessToken(): Flow<String>
    suspend fun saveAccessToken(token: String)
    fun getRefreshToken(): Flow<String>
    suspend fun saveRefreshToken(token: String)
    suspend fun isLoggedIn(): Boolean
}