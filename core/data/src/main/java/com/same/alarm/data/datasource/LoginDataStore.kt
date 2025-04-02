package com.same.alarm.data.datasource

import kotlinx.coroutines.flow.Flow

interface LoginDataStore {
    fun getAccessToken(): Flow<String>
    suspend fun saveLoginToken(token: String)
}