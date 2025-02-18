package com.same.alarm.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.same.alarm.database.model.AlarmLocal
import com.same.alarm.database.typeconverter.DayOfWeekConverter
import com.same.alarm.database.typeconverter.LocalTimeConverter

@Database(
    entities = [AlarmLocal::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(LocalTimeConverter::class, DayOfWeekConverter::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}