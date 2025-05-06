package com.same.alarm.ring

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.common.AlarmConstants.BUNDLE_KEY_ALARM_ID
import com.same.alarm.domain.repository.RingPlayer
import com.same.alarm.domain.repository.VibrationPlayer
import com.same.alarm.domain.usecase.CancelTodayAlarmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val ringPlayer: RingPlayer,
    private val vibrationPlayer: VibrationPlayer,
    private val cancelTodayAlarmsUseCase: CancelTodayAlarmsUseCase
) : ViewModel() {
    private val alarmId: Int = savedStateHandle[BUNDLE_KEY_ALARM_ID] ?: -1

    fun startRing() {
        ringPlayer.playRing()
        vibrationPlayer.startVibration()
    }

    fun stopRing() {
        ringPlayer.stopRing()
        vibrationPlayer.stopVibration()
    }

    fun cancelTodayAlarms() {
        viewModelScope.launch {
            cancelTodayAlarmsUseCase(alarmId)
        }
    }
}