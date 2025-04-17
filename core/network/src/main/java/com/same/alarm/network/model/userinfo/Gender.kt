package com.same.alarm.network.model.userinfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class Gender {
    @Json(name = "M")
    MALE,

    @Json(name = "F")
    FEMALE
}