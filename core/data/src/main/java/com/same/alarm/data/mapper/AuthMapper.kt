package com.same.alarm.data.mapper

import com.same.alarm.data.model.LoginRequestEntity
import com.same.alarm.data.model.LoginResponseEntity
import com.same.alarm.model.LoginRequest
import com.same.alarm.model.LoginResponse

object AuthMapper {
    fun LoginRequest.toEntity() = LoginRequestEntity(
        authType = authType.toString().lowercase()
    )

    fun LoginResponseEntity.toModel() = LoginResponse(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}