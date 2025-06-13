package com.same.alarm.data.datasource

import com.same.alarm.data.model.alarm.AlarmLocalEntity
import kotlinx.coroutines.flow.Flow

interface AlarmLocalDataSource {
    suspend fun addAlarm(alarmLocalEntity: AlarmLocalEntity): Long
    suspend fun removeAlarm(alarmId: Long)
    suspend fun updateAlarm(alarmLocalEntity: AlarmLocalEntity)
    fun getAllAlarms() : Flow<List<AlarmLocalEntity>>
    fun getAlarmById(alarmId: Long): Flow<AlarmLocalEntity?>
}