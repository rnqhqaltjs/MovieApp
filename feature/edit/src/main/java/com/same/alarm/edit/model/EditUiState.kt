package com.same.alarm.edit.model

sealed class EditState {
    data object Success : EditState()
    data class Failure(val error: String) : EditState()
}