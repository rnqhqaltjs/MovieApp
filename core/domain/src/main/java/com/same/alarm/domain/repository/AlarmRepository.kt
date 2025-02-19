package com.same.alarm.domain.repository

import com.same.alarm.model.Alarm

interface AlarmRepository {
    suspend fun addAlarm(alarm: Alarm)
}