package com.same.alarm.network.datasource

import com.same.alarm.data.datasource.UserDataSource
import com.same.alarm.data.model.userinfo.UserInfoRequestEntity
import com.same.alarm.data.model.userinfo.UserInfoResponseEntity
import com.same.alarm.network.mapper.UserMapper.toDto
import com.same.alarm.network.mapper.UserMapper.toEntity
import com.same.alarm.network.model.util.getBodyOrThrow
import com.same.alarm.network.remote.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override suspend fun getUserInfo(): UserInfoResponseEntity {
        return userService.getUserInfo().getBodyOrThrow().toEntity()
    }

    override suspend fun saveUserInfo(userInfoRequestEntity: UserInfoRequestEntity) {
        return userService.saveUserInfo(userInfoRequestEntity.toDto())
    }
}