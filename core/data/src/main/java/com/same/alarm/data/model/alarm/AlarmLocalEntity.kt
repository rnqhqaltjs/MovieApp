package com.same.alarm.data.model.alarm

import java.time.LocalTime

data class AlarmLocalEntity (
    val id: Long,
    val time: LocalTime,
    val title: String,
    val statusMessage: String,
    val daysOfWeek: List<Int>,
    val category: String,
    val isRepeating: Boolean,
    val isActive: Boolean,
    val isPinned: Boolean
)