package com.same.alarm.domain.repository

import com.same.alarm.model.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun addAlarm(alarm: Alarm)
    fun getAllAlarms() : Flow<List<Alarm>>
}