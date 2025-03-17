package com.same.alarm.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.usecase.LoadAlarmUseCase
import com.same.alarm.model.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val loadAlarmUseCase: LoadAlarmUseCase
) : ViewModel() {
    private val _alarmState = MutableStateFlow<Alarm?>(null)
    val alarmState: StateFlow<Alarm?> = _alarmState

    fun loadAlarm(alarmId: Int) {
        viewModelScope.launch {
            _alarmState.value = loadAlarmUseCase(alarmId)
        }
    }
}