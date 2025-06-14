package com.same.alarm.network.model.auth

import com.same.alarm.network.model.userinfo.UserInfoResponseDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponseDto(
    @Json(name = "accessToken")
    val accessToken: String,
    @Json(name = "refreshToken")
    val refreshToken: String,
    @Json(name = "user")
    val user: UserInfoResponseDto
)