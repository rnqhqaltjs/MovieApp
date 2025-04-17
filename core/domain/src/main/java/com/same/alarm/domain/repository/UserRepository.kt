package com.same.alarm.domain.repository

import com.same.alarm.model.userinfo.UserInfoRequest
import com.same.alarm.model.userinfo.UserInfoResponse

interface UserRepository {
    suspend fun getUserInfo(): UserInfoResponse
    suspend fun saveUserInfo(userInfoRequest: UserInfoRequest)
}