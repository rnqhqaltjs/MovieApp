package com.same.alarm.domain.usecase.alarm

import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CancelTodayAlarmsUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmHelper: AlarmHelper
) {
    suspend operator fun invoke(alarmId: Int) {
        alarmRepository.getAlarmById(alarmId).first()?.let { alarm ->
            alarmHelper.unSetTodayAlarms(alarm)
        }
    }
}