package com.same.alarm.ring

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.common.AlarmConstants.BUNDLE_KEY_ALARM_ID
import com.same.alarm.domain.usecase.alarm.AutoStopAlarmUseCase
import com.same.alarm.domain.usecase.alarm.CancelTodayAlarmsUseCase
import com.same.alarm.domain.usecase.alarm.FetchAlarmMessageUseCase
import com.same.alarm.domain.usecase.ring.StartPlayerUseCase
import com.same.alarm.domain.usecase.ring.StopPlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val startPlayerUseCase: StartPlayerUseCase,
    private val stopPlayerUseCase: StopPlayerUseCase,
    private val cancelTodayAlarmsUseCase: CancelTodayAlarmsUseCase,
    private val autoStopAlarmUseCase: AutoStopAlarmUseCase,
    private val fetchAlarmMessageUseCase: FetchAlarmMessageUseCase
) : ViewModel() {
    private val alarmId: Int = savedStateHandle[BUNDLE_KEY_ALARM_ID] ?: -1
    private val repeatCount: Int = savedStateHandle[BUNDLE_KEY_ALARM_ID] ?: 0

    private val _alarmMessage = MutableStateFlow("")
    val alarmMessage: StateFlow<String> = _alarmMessage.asStateFlow()

    init {
        autoStopAlarm()
        fetchAlarmMessage()
    }

    fun startPlayer() {
        startPlayerUseCase()
    }

    fun stopPlayer() {
        stopPlayerUseCase()
    }

    fun cancelTodayAlarms() {
        viewModelScope.launch {
            cancelTodayAlarmsUseCase(alarmId)
        }
    }

    fun fetchAlarmMessage() {
        viewModelScope.launch {
            val message = fetchAlarmMessageUseCase()
            _alarmMessage.value = message
        }
    }

    fun autoStopAlarm(force: Boolean = false) {
        viewModelScope.launch {
            autoStopAlarmUseCase(alarmId, repeatCount, force)
        }
    }
}