package com.same.alarm.domain.usecase.alarm

import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.alarm.Alarm
import javax.inject.Inject

class UpdateAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmHelper: AlarmHelper
) {
    suspend operator fun invoke(alarm: Alarm): Result<Unit> {
        return runCatching {
            alarmHelper.unScheduleAlarm(alarm)
            alarmRepository.updateAlarm(alarm)
            alarmHelper.scheduleAlarm(alarm)
        }
    }
}