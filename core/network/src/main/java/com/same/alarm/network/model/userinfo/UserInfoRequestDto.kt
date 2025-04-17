package com.same.alarm.network.model.userinfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoRequestDto(
    @Json(name = "name")
    val name: String,

    @Json(name = "age")
    val age: Int,

    @Json(name = "gender")
    val gender: Gender,

    @Json(name = "job")
    val job: String,

    @Json(name = "addr")
    val address: String
)