package com.same.alarm.data.model

import java.time.LocalTime

data class AlarmEntity (
    val id: Long,
    val time: LocalTime,
    val statusMessage: String,
    val daysOfWeek: List<Int>,
    val category: String,
    val isRepeating: Boolean,
    val isActive: Boolean,
    val isPinned: Boolean
)