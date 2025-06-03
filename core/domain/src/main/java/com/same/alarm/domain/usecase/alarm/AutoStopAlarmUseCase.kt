package com.same.alarm.domain.usecase.alarm

import com.same.alarm.common.AlarmConstants.REPEAT_COUNT
import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.Alarm
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AutoStopAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmHelper: AlarmHelper
) {
    suspend operator fun invoke(
        alarmId: Int,
        repeatCount: Int,
        force: Boolean = false
    ) {
        val alarm = alarmRepository.getAlarmById(alarmId).firstOrNull() ?: return

        // 1) force 모드면 조건 없이 비활성화
        if (force) {
            disableAlarm(alarm)
            return
        }

        // 2) 단일 알람: 바로 비활성화
        if (!alarm.isRepeating && alarm.daysOfWeek.isEmpty()) {
            disableAlarm(alarm)
            return
        }

        // 3) interval 반복(요일 없음): 마지막 회차면 비활성화
        if (alarm.isRepeating && alarm.daysOfWeek.isEmpty()) {
            if (repeatCount >= REPEAT_COUNT - 1) {
                disableAlarm(alarm)
            }
        }

        // 4) 요일 반복이 있는 경우에는 유지 (추가 규칙이 필요하면 여기서 처리)
    }

    private suspend fun disableAlarm(alarm: Alarm) {
        alarmHelper.unScheduleAlarm(alarm)
        alarmRepository.updateAlarm(alarm.copy(isActive = false))
    }
}