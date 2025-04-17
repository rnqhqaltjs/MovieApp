package com.same.alarm.data.datasource

import com.same.alarm.data.model.userinfo.UserInfoRequestEntity
import com.same.alarm.data.model.userinfo.UserInfoResponseEntity

interface UserDataSource {
    suspend fun getUserInfo(): UserInfoResponseEntity
    suspend fun saveUserInfo(userInfoRequestEntity: UserInfoRequestEntity)
}