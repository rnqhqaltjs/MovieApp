package com.same.alarm.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.usecase.LoadAlarmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val loadAlarmUseCase: LoadAlarmUseCase
) : ViewModel() {

    fun loadAlarm(alarmId: Int) {
        viewModelScope.launch {
             loadAlarmUseCase(alarmId)
        }
    }
}