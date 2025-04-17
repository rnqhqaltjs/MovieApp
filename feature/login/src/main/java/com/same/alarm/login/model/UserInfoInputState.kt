package com.same.alarm.login.model

sealed class UserInfoInputState {
    data object Success : UserInfoInputState()
    data class Failure(val error: String) : UserInfoInputState()
}