package com.same.alarm.network.mapper

import com.same.alarm.data.model.LoginRequestEntity
import com.same.alarm.data.model.LoginResponseEntity
import com.same.alarm.network.model.LoginRequestDto
import com.same.alarm.network.model.LoginResponseDto

object AuthMapper {
    fun LoginRequestEntity.toDto() = LoginRequestDto(
        authType = authType
    )

    fun LoginResponseDto.toEntity() = LoginResponseEntity(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}