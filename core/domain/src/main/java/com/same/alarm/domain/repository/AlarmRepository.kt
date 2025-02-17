package com.same.alarm.domain.repository

import com.same.alarm.model.Alarm

interface AlarmRepository {
    fun addAlarm(alarm: Alarm)
}