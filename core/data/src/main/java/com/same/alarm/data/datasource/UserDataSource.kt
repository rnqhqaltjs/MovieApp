package com.same.alarm.data.datasource

import com.same.alarm.data.model.userinfo.UserInfoRequestEntity
import com.same.alarm.data.model.userinfo.UserInfoResponseEntity
import java.io.File

interface UserDataSource {
    suspend fun getUserInfo(): UserInfoResponseEntity
    suspend fun saveUserInfo(userInfoRequestEntity: UserInfoRequestEntity, image: File?)
}