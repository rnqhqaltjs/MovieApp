package com.same.alarm.data.repository

import com.same.alarm.data.datasource.UserDataSource
import com.same.alarm.data.mapper.UserMapper.toEntity
import com.same.alarm.data.mapper.UserMapper.toModel
import com.same.alarm.domain.repository.UserRepository
import com.same.alarm.model.userinfo.UserInfoRequest
import com.same.alarm.model.userinfo.UserInfoResponse
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUserInfo(): UserInfoResponse {
        return userDataSource.getUserInfo().toModel()
    }

    override suspend fun saveUserInfo(userInfoRequest: UserInfoRequest) {
        return userDataSource.saveUserInfo(userInfoRequest.toEntity())
    }
}