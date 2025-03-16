package com.same.alarm.alarm

import androidx.lifecycle.ViewModel
import com.same.alarm.domain.repository.AlarmPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmPlayer: AlarmPlayer
) : ViewModel() {

    fun startAlarm() {
        alarmPlayer.playAlarm()
    }

    fun stopAlarm() {
        alarmPlayer.stopAlarm()
    }
}