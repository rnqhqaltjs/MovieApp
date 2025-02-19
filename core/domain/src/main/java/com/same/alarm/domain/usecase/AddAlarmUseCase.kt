package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.Alarm
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(alarm: Alarm) = alarmRepository.addAlarm(alarm)
}