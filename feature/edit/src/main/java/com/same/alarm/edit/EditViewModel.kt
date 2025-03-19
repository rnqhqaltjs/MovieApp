package com.same.alarm.edit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.same.alarm.common.Result
import com.same.alarm.common.asResult
import com.same.alarm.domain.usecase.LoadAlarmUseCase
import com.same.alarm.domain.usecase.UpdateAlarmUseCase
import com.same.alarm.model.Alarm
import com.same.alarm.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    loadAlarmUseCase: LoadAlarmUseCase,
    savedStateHandle: SavedStateHandle,
    private val updateAlarmUseCase: UpdateAlarmUseCase
) : ViewModel() {
    private val alarmId = savedStateHandle.toRoute<Route.Edit>().alarmId

    private val _selectedDays = MutableStateFlow<List<Int>>(emptyList())
    val selectedDays: StateFlow<List<Int>> = _selectedDays.asStateFlow()

    val alarmState: StateFlow<EditUiState> = loadAlarmUseCase(alarmId)
        .asResult()
        .map { result ->
            when (result) {
                is Result.Loading -> EditUiState.Loading
                is Result.Success -> {
                    _selectedDays.value = result.data?.daysOfWeek ?: emptyList()
                    EditUiState.Success(result.data)
                }
                is Result.Error -> EditUiState.Failure(result.exception)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EditUiState.Loading
        )

    fun toggleDay(day: Int) {
        _selectedDays.update { currentDays ->
            if (day in currentDays) {
                currentDays - day
            } else {
                (currentDays + day).sorted()
            }
        }
    }

    fun updateAlarm(alarm: Alarm) {
        viewModelScope.launch {
            Log.d("alarm", alarm.toString())
            updateAlarmUseCase(alarm)
        }
    }
}

sealed interface EditUiState {
    data class Success(
        val data: Alarm?,
    ) : EditUiState

    data class Failure(
        val t: Throwable?,
    ) : EditUiState

    data object Loading : EditUiState
}