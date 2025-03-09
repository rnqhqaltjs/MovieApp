package com.same.alarm.data.datasource

import com.same.alarm.data.model.AlarmEntity
import kotlinx.coroutines.flow.Flow

interface AlarmDataSource {
    suspend fun addAlarm(alarmEntity: AlarmEntity)
    fun getAllAlarms() : Flow<List<AlarmEntity>>
    suspend fun removeAlarm(alarmEntity: AlarmEntity)
}