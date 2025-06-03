package com.same.alarm.domain.usecase.alarm

import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.Alarm
import javax.inject.Inject

class TogglePinUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(alarm: Alarm) =
        alarmRepository.updateAlarm(alarm)
}