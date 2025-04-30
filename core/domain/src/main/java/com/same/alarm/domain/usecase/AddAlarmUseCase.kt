package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.Alarm
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmHelper: AlarmHelper
) {
    suspend operator fun invoke(alarm: Alarm): Result<Unit> {
        return runCatching {
            validateAlarm(alarm)

            val alarmId = alarmRepository.addAlarm(alarm)
            alarmHelper.scheduleAlarm(alarm.copy(id = alarmId))
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