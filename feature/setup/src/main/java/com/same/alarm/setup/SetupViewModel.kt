package com.same.alarm.setup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.usecase.AddAlarmUseCase
import com.same.alarm.model.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val addAlarmUseCase: AddAlarmUseCase
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage.asStateFlow()

    private val _selectedDays = MutableStateFlow<List<Int>>(emptyList())
    val selectedDays: StateFlow<List<Int>> = _selectedDays.asStateFlow()

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _addEvent = MutableSharedFlow<Unit>(replay = 0)
    val addEvent: SharedFlow<Unit> = _addEvent

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateStatusMessage(newMessage: String) {
        _statusMessage.value = newMessage
    }

    fun toggleDay(day: Int) {
        _selectedDays.update { currentDays ->
            if (day in currentDays) {
                currentDays - day
            } else {
                (currentDays + day).sorted()
            }
        }
    }

    fun toggleCategory(category: String) {
        _selectedCategory.update { currentCategory ->
            if (currentCategory == category) "" else category
        }
    }

    fun addAlarm(alarm: Alarm) {
        viewModelScope.launch {
            addAlarmUseCase(alarm).onSuccess {
                _addEvent.emit(Unit)
            }
        }
    }
}