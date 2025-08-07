package com.same.alarm.domain.usecase.alarm

import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.alarm.Alarm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    operator fun invoke(alarmId: Int): Flow<Alarm?> = alarmRepository.getAlarmById(alarmId)
}