package com.same.alarm.domain.repository

import com.same.alarm.model.Alarm

interface AlarmHelper {
    fun scheduleAlarm(alarm: Alarm)
    fun unScheduleAlarm(alarm: Alarm)
}