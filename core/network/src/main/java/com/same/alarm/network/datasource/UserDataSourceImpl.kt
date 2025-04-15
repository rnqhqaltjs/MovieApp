package com.same.alarm.network.datasource

import com.same.alarm.data.datasource.UserDataSource
import com.same.alarm.data.model.UserInfoResponseEntity
import com.same.alarm.network.mapper.UserMapper.toEntity
import com.same.alarm.network.remote.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override suspend fun getUserInfo(): UserInfoResponseEntity {
        return userService.getUserInfo().toEntity()
    }

}