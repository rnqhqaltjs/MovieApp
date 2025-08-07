package com.same.alarm.data.mapper

import com.same.alarm.data.model.login.LoginRequestEntity
import com.same.alarm.data.model.login.LoginResponseEntity
import com.same.alarm.model.login.LoginRequest
import com.same.alarm.model.login.LoginResponse

object AuthMapper {
    fun LoginRequest.toEntity() = LoginRequestEntity(
        authType = authType.toString().lowercase()
    )

    fun LoginResponseEntity.toDomain() = LoginResponse(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}