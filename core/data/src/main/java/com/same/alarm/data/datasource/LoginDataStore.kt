package com.same.alarm.data.datasource

import kotlinx.coroutines.flow.Flow

interface LoginDataStore {
    val accessToken: Flow<String>
    suspend fun saveLoginToken(token: String)
}