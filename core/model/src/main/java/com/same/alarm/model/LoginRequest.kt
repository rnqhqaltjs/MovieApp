package com.same.alarm.model

data class LoginRequest(
    val authType: AuthType
)
enum class AuthType {
    KAKAO, GOOGLE,
}