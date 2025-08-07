package com.same.alarm.network.model.util

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    @Json(name= "success")
    val success: Boolean,
    @Json(name = "message")
    val message: String,
    @Json(name = "body")
    val body: T
)

inline fun <T> ApiResponse<T>.getBodyOrThrow(): T {
    if (!success) {
        throw IllegalStateException("API 처리 실패: $message")
    }
    return body
}