package com.same.alarm.domain.repository

import com.same.alarm.model.UserInfoResponse

interface UserRepository {
    suspend fun getUserInfo(): UserInfoResponse
}