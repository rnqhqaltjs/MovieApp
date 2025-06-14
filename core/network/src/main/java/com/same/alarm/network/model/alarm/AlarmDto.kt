package com.same.alarm.network.model.alarm

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalTime

@JsonClass(generateAdapter = true)
data class AlarmDto (
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val statusMessage: String,
    @Json(name = "category")
    val category: String,
    @Json(name = "time")
    val time: String,
    @Json(name = "dayOfWeek")
    val daysOfWeek: List<Int>,
    @Json(name = "repeating")
    val isRepeating: Boolean
)