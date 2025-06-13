package com.same.alarm.database.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.same.alarm.database.model.AlarmLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmLocal: AlarmLocal): Long

    @Query("SELECT * FROM alarms")
    fun getAllAlarms(): Flow<List<AlarmLocal>>

    @Query("DELETE FROM alarms WHERE id = :alarmId")
    suspend fun deleteAlarm(alarmId: Long)

    @Update
    suspend fun updateAlarm(alarmLocal: AlarmLocal)

    @Query("SELECT * FROM alarms WHERE id = :alarmId LIMIT 1")
    fun getAlarmById(alarmId: Long): Flow<AlarmLocal?>
}