package com.same.alarm.login.model

sealed class LoginState {
    data class Success(val isNewUser: Boolean) : LoginState()
    data class Failure(val error: String) : LoginState()
}