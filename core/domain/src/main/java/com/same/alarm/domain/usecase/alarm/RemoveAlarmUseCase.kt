package com.same.alarm.domain.usecase.alarm

import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.Alarm
import javax.inject.Inject

class RemoveAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmHelper: AlarmHelper
) {
    suspend operator fun invoke(alarm: Alarm) {
        alarmRepository.removeAlarm(alarm)
        alarmHelper.unScheduleAlarm(alarm)
    }
}