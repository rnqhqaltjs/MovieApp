package com.same.alarm.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalTime

@Entity(tableName = "alarms")
data class AlarmLocal (
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val time: LocalTime,
    val statusMessage: String,
    val daysOfWeek: Set<DayOfWeek>,
    val category: String,
    val isRepeating: Boolean
)