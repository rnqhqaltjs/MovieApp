package com.same.alarm.ring

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.common.AlarmConstants.BUNDLE_KEY_ALARM_ID
import com.same.alarm.common.AlarmConstants.BUNDLE_KEY_REPEAT_COUNT
import com.same.alarm.domain.usecase.alarm.AutoStopAlarmUseCase
import com.same.alarm.domain.usecase.alarm.CancelTodayAlarmsUseCase
import com.same.alarm.domain.usecase.alarm.FetchAlarmMessageUseCase
import com.same.alarm.domain.usecase.alarm.LoadAlarmUseCase
import com.same.alarm.domain.usecase.ring.StartPlayerUseCase
import com.same.alarm.domain.usecase.ring.StopPlayerUseCase
import com.same.alarm.model.alarm.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val startPlayerUseCase: StartPlayerUseCase,
    private val stopPlayerUseCase: StopPlayerUseCase,
    private val cancelTodayAlarmsUseCase: CancelTodayAlarmsUseCase,
    private val autoStopAlarmUseCase: AutoStopAlarmUseCase,
    private val fetchAlarmMessageUseCase: FetchAlarmMessageUseCase,
    private val loadAlarmUseCase: LoadAlarmUseCase
) : ViewModel() {
    private val alarmId: Int = savedStateHandle[BUNDLE_KEY_ALARM_ID] ?: -1
    private val repeatCount: Int = savedStateHandle[BUNDLE_KEY_REPEAT_COUNT] ?: 0

    private val _alarmMessage = MutableStateFlow<List<String>>(listOf())
    val alarmMessage: StateFlow<List<String>> = _alarmMessage.asStateFlow()

    val alarm: StateFlow<Alarm?> = loadAlarmUseCase(alarmId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

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
            alarm
                .filterNotNull()
                .firstOrNull()
                ?.let { alarm ->
                    val messages = fetchAlarmMessageUseCase(alarm.time.format(DateTimeFormatter.ofPattern("HH:mm")))
                    _alarmMessage.value = messages
                }
        }
    }

    fun autoStopAlarm(force: Boolean = false) {
        viewModelScope.launch {
            autoStopAlarmUseCase(alarmId, repeatCount, force)
        }
    }
}