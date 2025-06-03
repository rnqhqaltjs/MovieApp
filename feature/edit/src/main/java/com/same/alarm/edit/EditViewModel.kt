package com.same.alarm.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.same.alarm.domain.usecase.alarm.LoadAlarmUseCase
import com.same.alarm.domain.usecase.alarm.UpdateAlarmUseCase
import com.same.alarm.model.Alarm
import com.same.alarm.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    loadAlarmUseCase: LoadAlarmUseCase,
    savedStateHandle: SavedStateHandle,
    private val updateAlarmUseCase: UpdateAlarmUseCase
) : ViewModel() {
    private val alarmId = savedStateHandle.toRoute<Route.Edit>().alarmId

    private val _selectedDays = MutableStateFlow<List<Int>>(emptyList())
    val selectedDays: StateFlow<List<Int>> = _selectedDays.asStateFlow()

    private val _updateEvent = MutableSharedFlow<Unit>(replay = 0)
    val updateEvent: SharedFlow<Unit> = _updateEvent

    val alarmState: StateFlow<Alarm?> = loadAlarmUseCase(alarmId)
        .onEach { alarm ->
            alarm?.let {
                _selectedDays.update { alarm.daysOfWeek }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun toggleDay(day: Int) {
        _selectedDays.update { currentDays ->
            if (day in currentDays) {
                currentDays - day
            } else {
                (currentDays + day).sorted()
            }
        }
    }

    fun updateAlarm(alarm: Alarm) {
        viewModelScope.launch {
            updateAlarmUseCase(alarm).onSuccess {
                _updateEvent.emit(Unit)
            }
        }
    }
}