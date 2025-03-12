package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.AlarmRepository
import javax.inject.Inject

class LoadAlarmListUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    operator fun invoke() = alarmRepository.getAllAlarms()
}