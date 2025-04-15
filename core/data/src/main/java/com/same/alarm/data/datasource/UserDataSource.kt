package com.same.alarm.data.datasource

import com.same.alarm.data.model.UserInfoResponseEntity

interface UserDataSource {
    suspend fun getUserInfo(): UserInfoResponseEntity
}