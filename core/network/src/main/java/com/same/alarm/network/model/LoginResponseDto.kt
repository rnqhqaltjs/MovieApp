package com.same.alarm.network.model

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String
)