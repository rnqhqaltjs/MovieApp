package com.same.alarm.domain.usecase.alarm

import com.same.alarm.domain.repository.AlarmRepository
import javax.inject.Inject

class FetchAlarmMessageUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
) {
    suspend operator fun invoke() : String = alarmRepository.getAlarmMessage()
}