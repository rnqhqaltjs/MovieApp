package com.same.alarm.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.same.alarm.common.Constants.CATEGORIES
import com.same.alarm.domain.usecase.LoadAlarmListUseCase
import com.same.alarm.domain.usecase.RemoveAlarmUseCase
import com.same.alarm.domain.usecase.ToggleAlarmUseCase
import com.same.alarm.domain.usecase.TogglePinUseCase
import com.same.alarm.model.Alarm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    loadAlarmListUseCase: LoadAlarmListUseCase,
    private val removeAlarmUseCase: RemoveAlarmUseCase,
    private val toggleAlarmUseCase: ToggleAlarmUseCase,
    private val togglePinUseCase: TogglePinUseCase
) : ViewModel() {
    val categories = listOf("전체") + CATEGORIES

    private val _selectedCategory = MutableStateFlow("전체")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    val alarmListState: StateFlow<List<Alarm>> = combine(
        loadAlarmListUseCase(),
        _selectedCategory
    ) { alarmList, category ->
        alarmList.filter { alarm ->
            category == "전체" || alarm.category == category
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private val _revealedState = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val revealedState: StateFlow<Map<Int, Boolean>> = _revealedState

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun removeAlarm(alarm: Alarm) {
        viewModelScope.launch {
            removeAlarmUseCase(alarm)
        }
    }

    fun toggleAlarm(alarm: Alarm) {
        viewModelScope.launch {
            toggleAlarmUseCase(alarm)
        }
    }

    fun swipeRevealed(alarmId: Int, isRevealed: Boolean) {
        _revealedState.update { mapOf(alarmId to isRevealed) }
    }

    fun resetRevealedState() {
        _revealedState.update { emptyMap() }
    }

    fun togglePin(alarm: Alarm) {
        viewModelScope.launch {
            togglePinUseCase(alarm.copy(isPinned = !alarm.isPinned))
        }
    }
}