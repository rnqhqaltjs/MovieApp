package com.same.alarm.domain.repository

import android.net.Uri
import com.same.alarm.model.userinfo.UserInfoRequest
import com.same.alarm.model.userinfo.UserInfoResponse
import java.io.File

interface UserRepository {
    suspend fun getUserInfo(): UserInfoResponse
    suspend fun saveUserInfo(userInfoRequest: UserInfoRequest, image: Uri?)
}