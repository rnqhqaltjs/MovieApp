package com.same.alarm.model.login

data class LoginRequest(
    val authType: AuthType
)
enum class AuthType {
    KAKAO, GOOGLE,
}