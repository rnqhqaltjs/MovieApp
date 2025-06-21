package com.same.alarm.domain.repository

import com.same.alarm.model.alarm.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    suspend fun addAlarm(alarm: Alarm): Int
    suspend fun removeAlarm(alarmId: Int)
    suspend fun updateAlarm(alarm: Alarm)
    fun getAllAlarms() : Flow<List<Alarm>>
    fun getAlarmById(alarmId: Int): Flow<Alarm?>
    suspend fun getAlarmMessage(): String
}