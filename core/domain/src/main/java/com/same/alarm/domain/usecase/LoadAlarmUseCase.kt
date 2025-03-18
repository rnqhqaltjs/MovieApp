package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.AlarmRepository
import javax.inject.Inject

class LoadAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    operator fun invoke(alarmId: Int) = alarmRepository.getAlarmById(alarmId)
}