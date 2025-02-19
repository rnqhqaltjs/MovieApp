package com.same.alarm.data.datasource

import com.same.alarm.data.model.AlarmEntity

interface AlarmDataSource {
    suspend fun addAlarm(alarmEntity: AlarmEntity)
}