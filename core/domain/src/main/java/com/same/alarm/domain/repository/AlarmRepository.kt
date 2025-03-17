package com.same.alarm.domain.repository

import com.same.alarm.model.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun addAlarm(alarm: Alarm): Int
    suspend fun removeAlarm(alarm: Alarm)
    suspend fun updateAlarm(alarm: Alarm)
    fun getAllAlarms() : Flow<List<Alarm>>
    suspend fun getAlarmById(alarmId: Int): Alarm?
}