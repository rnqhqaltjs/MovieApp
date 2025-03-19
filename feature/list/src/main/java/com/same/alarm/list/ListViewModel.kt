package com.same.alarm.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.usecase.LoadAlarmListUseCase
import com.same.alarm.domain.usecase.RemoveAlarmUseCase
import com.same.alarm.domain.usecase.ToggleAlarmUseCase
import com.same.alarm.model.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    loadAlarmListUseCase: LoadAlarmListUseCase,
    private val removeAlarmUseCase: RemoveAlarmUseCase,
    private val toggleAlarmUseCase: ToggleAlarmUseCase
) : ViewModel() {
    val alarmListState: StateFlow<List<Alarm>> = loadAlarmListUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _revealedState = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val revealedState: StateFlow<Map<Int, Boolean>> = _revealedState

    fun removeAlarm(alarm: Alarm) {
        viewModelScope.launch {
            removeAlarmUseCase(alarm)
        }
    }

    fun toggleAlarm(alarm: Alarm) {
        viewModelScope.launch {
            toggleAlarmUseCase(alarm)
        }
    }

    fun swipeRevealed(alarmId: Int, isRevealed: Boolean) {
        _revealedState.update { mapOf(alarmId to isRevealed) }
    }

    fun resetRevealedState() {
        _revealedState.update { emptyMap() }
    }
}