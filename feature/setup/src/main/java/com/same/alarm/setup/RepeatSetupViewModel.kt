package com.same.alarm.setup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RepeatSetupViewModel @Inject constructor(
) : ViewModel() {
    private val _selectedDays = MutableStateFlow<List<String>>(emptyList())
    val selectedDays: StateFlow<List<String>> = _selectedDays.asStateFlow()

    fun toggleDay(day: String) {
        _selectedDays.update { currentDays ->
            val days = listOf("월", "화", "수", "목", "금", "토", "일")
            if (day in currentDays) {
                currentDays - day
            } else {
                (currentDays + day).sortedBy { days.indexOf(it) }
            }
        }
    }

    fun clearSelectedDays() {
        _selectedDays.value = emptyList()
    }
}