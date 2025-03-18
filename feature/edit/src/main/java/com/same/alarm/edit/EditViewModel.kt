package com.same.alarm.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.same.alarm.domain.usecase.LoadAlarmUseCase
import com.same.alarm.model.Alarm
import com.same.alarm.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    loadAlarmUseCase: LoadAlarmUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val alarmId = savedStateHandle.toRoute<Route.Edit>().alarmId

    val alarmState: StateFlow<Alarm?> = loadAlarmUseCase(alarmId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )
}