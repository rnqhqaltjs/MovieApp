package com.same.alarm.setup.model

sealed class SetupState {
    data object Success : SetupState()
    data class Failure(val error: String) : SetupState()
}