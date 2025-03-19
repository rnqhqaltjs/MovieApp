package com.same.alarm.domain.usecase

import android.util.Log
import com.same.alarm.domain.repository.AlarmHelper
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.Alarm
import javax.inject.Inject

class UpdateAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val alarmHelper: AlarmHelper
) {
    suspend operator fun invoke(alarm: Alarm) {
        try {
            // 알람 취소 및 재설정 시도
            alarmHelper.unScheduleAlarm(alarm)
            alarmRepository.updateAlarm(alarm)
            alarmHelper.scheduleAlarm(alarm)
            Log.d("UpdateAlarmUseCase", "Alarm successfully updated: $alarm")
        } catch (e: Exception) {
            Log.e("UpdateAlarmUseCase", "Failed to update alarm", e)
            throw e  // 예외를 다시 던져서 ViewModel에서 처리할 수 있도록
        }
    }
}