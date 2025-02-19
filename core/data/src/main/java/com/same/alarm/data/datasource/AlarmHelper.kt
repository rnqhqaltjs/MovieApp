package com.same.alarm.data.datasource

import com.same.alarm.model.Alarm

interface AlarmHelper {
    fun setAlarm(alarm: Alarm)
    fun cancelAlarm(alarm: Alarm)
}