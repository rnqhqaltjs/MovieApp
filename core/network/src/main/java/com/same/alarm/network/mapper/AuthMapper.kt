package com.same.alarm.network.mapper

import com.same.alarm.data.model.login.LoginRequestEntity
import com.same.alarm.data.model.login.LoginResponseEntity
import com.same.alarm.network.model.login.LoginRequestDto
import com.same.alarm.network.model.login.LoginResponseDto

object AuthMapper {
    fun LoginRequestEntity.toDto() = LoginRequestDto(
        authType = authType
    )

    fun LoginResponseDto.toEntity() = LoginResponseEntity(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}