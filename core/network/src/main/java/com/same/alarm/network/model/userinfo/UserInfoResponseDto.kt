package com.same.alarm.network.model.userinfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoResponseDto(
    @Json(name = "name")
    val name: String,
    @Json(name = "oauthProvider")
    val oauthProvider: String,
    @Json(name = "oauthId")
    val oauthId: String,
    @Json(name = "role")
    val role: String,
    @Json(name = "age")
    val age: Int?,
    @Json(name = "gender")
    val gender: Gender?,
    @Json(name = "job")
    val job: String?,
    @Json(name = "addr")
    val address: String?
)