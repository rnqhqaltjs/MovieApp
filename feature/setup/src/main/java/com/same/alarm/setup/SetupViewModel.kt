package com.same.alarm.setup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.usecase.alarm.AddAlarmUseCase
import com.same.alarm.model.alarm.Alarm
import com.same.alarm.setup.model.SetupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val addAlarmUseCase: AddAlarmUseCase
) : ViewModel() {
    private val _time = MutableStateFlow(LocalTime.of(9, 0))
    val time: StateFlow<LocalTime> = _time.asStateFlow()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage.asStateFlow()

    private val _selectedDays = MutableStateFlow<List<Int>>(emptyList())
    val selectedDays: StateFlow<List<Int>> = _selectedDays.asStateFlow()

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _isAlarmRepeated = MutableStateFlow(false)
    val isAlarmRepeated: StateFlow<Boolean> = _isAlarmRepeated.asStateFlow()

    private val _addEvent = MutableSharedFlow<SetupState>()
    val addEvent: SharedFlow<SetupState> = _addEvent

    fun updateTime(newTime: LocalTime) {
        _time.value = newTime
    }

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

    fun toggleRepeat(isRepeated: Boolean) {
        _isAlarmRepeated.value = isRepeated
    }

    fun addAlarm() {
        viewModelScope.launch {
            val alarm = Alarm(
                time = _time.value,
                title = _title.value,
                statusMessage = _statusMessage.value,
                daysOfWeek = _selectedDays.value,
                category = _selectedCategory.value,
                isRepeating = _isAlarmRepeated.value
            )

            addAlarmUseCase(alarm)
                .onSuccess {
                    _addEvent.emit(SetupState.Success)
                    clearData()
                }
                .onFailure { error ->
                    error.message?.let { message ->
                        _addEvent.emit(SetupState.Failure(message))
                        Log.d("SetupViewModel", message)
                    }
                }
        }
    }

    fun clearData() {
        _time.value = LocalTime.of(9, 0)
        _title.value = ""
        _statusMessage.value = ""
        _selectedDays.value = emptyList()
        _selectedCategory.value = ""
        _isAlarmRepeated.value = false
    }
}