package com.same.alarm.edit.model

sealed class EditUiState<out T> {
    data object Idle : EditUiState<Nothing>()
    data object Loading : EditUiState<Nothing>()
    data class Success<T>(val data: T?) : EditUiState<T>()
    data class Error<T>(val message: String) : EditUiState<T>()
}