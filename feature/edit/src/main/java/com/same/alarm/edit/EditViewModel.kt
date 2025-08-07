package com.same.alarm.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.usecase.alarm.LoadAlarmUseCase
import com.same.alarm.domain.usecase.alarm.UpdateAlarmUseCase
import com.same.alarm.edit.model.EditState
import com.same.alarm.model.alarm.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val loadAlarmUseCase: LoadAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val alarmId: Int = savedStateHandle.get<Int>("alarmId") ?: -1

    private val _time = MutableStateFlow(LocalTime.of(9, 0))
    val time: StateFlow<LocalTime> = _time.asStateFlow()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage.asStateFlow()

    private val _selectedDays = MutableStateFlow<List<Int>>(emptyList())
    val selectedDays: StateFlow<List<Int>> = _selectedDays.asStateFlow()

    private val _selectedCategory = MutableStateFlow("스터디")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _isAlarmRepeated = MutableStateFlow(false)
    val isAlarmRepeated: StateFlow<Boolean> = _isAlarmRepeated.asStateFlow()

    private val _updateEvent = MutableSharedFlow<EditState>(replay = 0)
    val updateEvent: SharedFlow<EditState> = _updateEvent

    init {
        loadAlarm(alarmId)
    }

    fun loadAlarm(alarmId: Int) {
        viewModelScope.launch {
            loadAlarmUseCase(alarmId)
                .collectLatest { alarm ->
                    alarm?.let { alarm ->
                        _time.update { LocalTime.of(alarm.time.hour, alarm.time.minute) }
                        _title.update { alarm.title }
                        _statusMessage.update { alarm.statusMessage }
                        _selectedCategory.update { alarm.category }
                        _isAlarmRepeated.update { alarm.isRepeating }
                        _selectedDays.update { alarm.daysOfWeek }
                    }
                }
        }
    }

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateStatusMessage(newMessage: String) {
        _statusMessage.value = newMessage
    }

    fun updateTime(newTime: LocalTime) {
        _time.value = newTime
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
            if (currentCategory == category) currentCategory else category
        }
    }

    fun toggleRepeat(isRepeated: Boolean) {
        _isAlarmRepeated.value = isRepeated
    }

    fun updateAlarm() {
        viewModelScope.launch {
            val alarm = Alarm(
                id = alarmId,
                time = _time.value,
                title = _title.value,
                statusMessage = _statusMessage.value,
                daysOfWeek = _selectedDays.value,
                category = _selectedCategory.value,
                isRepeating = _isAlarmRepeated.value
            )

            updateAlarmUseCase(alarm)
                .onSuccess {
                    _updateEvent.emit(EditState.Success)
                    clearData()
                }
                .onFailure { error ->
                    error.message?.let { message ->
                        _updateEvent.emit(EditState.Failure(message))
                    }
                }
        }
    }

    fun clearData() {
        _time.value = LocalTime.of(9, 0)
        _title.value = ""
        _statusMessage.value = ""
        _selectedDays.value = emptyList()
        _selectedCategory.value = "스터디"
        _isAlarmRepeated.value = false
    }
}