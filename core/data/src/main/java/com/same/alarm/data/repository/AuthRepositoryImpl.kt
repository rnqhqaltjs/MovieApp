package com.same.alarm.data.repository

import com.same.alarm.data.datasource.LoginDataSource
import com.same.alarm.data.datasource.LoginDataStore
import com.same.alarm.data.mapper.AuthMapper.toDomain
import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.model.Login
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val loginDataStore: LoginDataStore,
    private val loginDataSource: LoginDataSource
) : AuthRepository {
    override suspend fun saveAccessToken(token: String) {
        return loginDataStore.saveLoginToken(token)
    }

    override suspend fun login(kakaoToken: String): Login {
        return loginDataSource.login(kakaoToken).toDomain()
    }
}