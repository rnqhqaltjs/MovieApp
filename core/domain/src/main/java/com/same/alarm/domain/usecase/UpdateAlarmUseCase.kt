package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.Alarm
import javax.inject.Inject

class UpdateAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmHelper: AlarmHelper
) {
    suspend operator fun invoke(alarm: Alarm): Result<Unit> {
        return try {
            alarmRepository.updateAlarm(alarm)
            if (alarm.isActive) {
                alarmHelper.scheduleAlarm(alarm)
            } else {
                alarmHelper.unScheduleAlarm(alarm)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}