package com.same.alarm.data.model

import java.time.DayOfWeek
import java.time.LocalTime

data class AlarmEntity (
    val id: Long,
    val time: LocalTime,
    val statusMessage: String,
    val daysOfWeek: Set<DayOfWeek>,
    val category: String,
    val isRepeating: Boolean
)