package com.same.alarm.data.datasource

import com.same.alarm.data.model.alarm.AlarmRemoteEntity

interface AlarmRemoteDataSource {
    suspend fun saveAlarm(alarmRemoteEntity: AlarmRemoteEntity): Long
    suspend fun deleteAlarm(alarmId: Long)
    suspend fun updateAlarm(alarmId: Long, alarmRemoteEntity: AlarmRemoteEntity): AlarmRemoteEntity
    suspend fun getAlarmById(alarmId: Long): AlarmRemoteEntity
    suspend fun getAlarmMessage(): String
}