package com.same.alarm.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.repository.AlarmPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmPlayer: AlarmPlayer
) : ViewModel() {

    fun startAlarm() {
        viewModelScope.launch {
            alarmPlayer.playAlarm()
        }
    }

    fun stopAlarm() {
        viewModelScope.launch {
            alarmPlayer.stopAlarm()
        }
    }
}