package com.same.alarm.ring

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.common.AlarmConstants.BUNDLE_KEY_ALARM_ID
import com.same.alarm.domain.repository.RingPlayer
import com.same.alarm.domain.usecase.AutoStopAlarmUseCase
import com.same.alarm.domain.usecase.CancelTodayAlarmsUseCase
import com.same.alarm.domain.usecase.StartPlayerUseCase
import com.same.alarm.domain.usecase.StopPlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val startPlayerUseCase: StartPlayerUseCase,
    private val stopPlayerUseCase: StopPlayerUseCase,
    private val cancelTodayAlarmsUseCase: CancelTodayAlarmsUseCase,
    private val autoStopAlarmUseCase: AutoStopAlarmUseCase
) : ViewModel() {
    private val alarmId: Int = savedStateHandle[BUNDLE_KEY_ALARM_ID] ?: -1
    private val repeatCount: Int = savedStateHandle[BUNDLE_KEY_ALARM_ID] ?: 0

    init {
        autoStopAlarm()
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

    fun autoStopAlarm(force: Boolean = false) {
        viewModelScope.launch {
            autoStopAlarmUseCase(alarmId, repeatCount, force)
        }
    }
}