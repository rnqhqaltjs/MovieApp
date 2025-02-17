package com.same.alarm.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.domain.usecase.AddAlarmUseCase
import com.same.alarm.model.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetUpViewModel @Inject constructor(
    private val addAlarmUseCase: AddAlarmUseCase
) : ViewModel() {

    fun addAlarm(alarm: Alarm) {
        viewModelScope.launch {
            addAlarmUseCase(alarm)
        }
    }
}