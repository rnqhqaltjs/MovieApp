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
            validateAlarm(alarm)

            alarmHelper.unScheduleAlarm(alarm)
            alarmRepository.updateAlarm(alarm)
            alarmHelper.scheduleAlarm(alarm)
        }
    }

    private fun validateAlarm(alarm: Alarm) {
        when {
            alarm.title.isBlank() -> throw IllegalArgumentException("제목이 비어있습니다.")
            alarm.statusMessage.isBlank() -> throw IllegalArgumentException("상태 메시지가 비어있습니다.")
            alarm.category.isBlank() -> throw IllegalArgumentException("카테고리가 선택되지 않았습니다.")
        }
    }
}