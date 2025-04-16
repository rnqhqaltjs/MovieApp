package com.same.alarm.network.mapper

import com.same.alarm.data.model.LoginRequestEntity
import com.same.alarm.data.model.LoginResponseEntity
import com.same.alarm.network.model.LoginRequestDto
import com.same.alarm.network.model.LoginResponseDto
import com.same.alarm.network.model.ReissueResponseDto

object AuthMapper {
    fun LoginRequestEntity.toDto() = LoginRequestDto(
        authType = authType
    )

    fun LoginResponseDto.toEntity() = LoginResponseEntity(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}