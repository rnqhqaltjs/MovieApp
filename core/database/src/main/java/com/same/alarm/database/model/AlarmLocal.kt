package com.same.alarm.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalTime

@Entity(tableName = "alarm_table")
data class AlarmLocal (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val time: LocalTime,
    val title: String,
    val daysOfWeek: Set<DayOfWeek>
)