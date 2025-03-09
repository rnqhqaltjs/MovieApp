package com.same.alarm.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.usecase.AddAlarmUseCase
import com.same.alarm.domain.usecase.LoadAlarmListUseCase
import com.same.alarm.domain.usecase.RemoveAlarmUseCase
import com.same.alarm.model.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val loadAlarmListUseCase: LoadAlarmListUseCase,
    private val removeAlarmUseCase: RemoveAlarmUseCase
) : ViewModel() {

    private val _alarmList = MutableStateFlow<List<Alarm>>(emptyList())
    val alarmList: StateFlow<List<Alarm>> = _alarmList.asStateFlow()

    init {
        loadAlarms()
    }

    private fun loadAlarms() {
        viewModelScope.launch {
            loadAlarmListUseCase()
                .collect { alarms -> _alarmList.value = alarms }
        }
    }

    fun removeAlarm(alarm: Alarm) {
        viewModelScope.launch {
            removeAlarmUseCase(alarm)
        }
    }
}