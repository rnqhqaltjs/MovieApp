package com.same.alarm.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.usecase.LoadAlarmListUseCase
import com.same.alarm.domain.usecase.RemoveAlarmUseCase
import com.same.alarm.domain.usecase.UpdateAlarmUseCase
import com.same.alarm.model.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    loadAlarmListUseCase: LoadAlarmListUseCase,
    private val removeAlarmUseCase: RemoveAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase
) : ViewModel() {
    val alarmList: StateFlow<List<Alarm>> = loadAlarmListUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun removeAlarm(alarm: Alarm) {
        viewModelScope.launch {
            removeAlarmUseCase(alarm)
        }
    }

    fun updateAlarm(alarm: Alarm) {
        viewModelScope.launch {
            updateAlarmUseCase(alarm)
        }
    }
}