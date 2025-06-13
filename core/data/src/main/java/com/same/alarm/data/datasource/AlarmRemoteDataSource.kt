package com.same.alarm.data.datasource

import com.same.alarm.data.model.alarm.AlarmRemoteEntity

interface AlarmRemoteDataSource {
    suspend fun saveAlarm(alarmRemoteEntity: AlarmRemoteEntity)
    suspend fun deleteAlarm(alarmId: Long)
    suspend fun updateAlarm(alarmId: Long, alarmRemoteEntity: AlarmRemoteEntity)
    suspend fun getAlarmById(alarmId: Long): AlarmRemoteEntity
}