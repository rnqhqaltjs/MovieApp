package com.same.alarm.database.datasource

import com.same.alarm.data.datasource.AlarmDataSource
import com.same.alarm.data.model.AlarmEntity
import com.same.alarm.database.mapper.AlarmMapper.toEntity
import com.same.alarm.database.room.AlarmDao
import com.same.alarm.database.mapper.AlarmMapper.toLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmDataSourceImpl @Inject constructor(
    private val alarmDao: AlarmDao
): AlarmDataSource {

    override suspend fun addAlarm(alarmEntity: AlarmEntity) {
        return alarmDao.insertAlarm(alarmEntity.toLocal())
    }

    override fun getAllAlarms(): Flow<List<AlarmEntity>> {
        return alarmDao.getAllAlarms()
            .map {
                it.map { alarmLocal -> alarmLocal.toEntity() }
            }
    }

    override suspend fun removeAlarm(alarmEntity: AlarmEntity) {
        return alarmDao.deleteAlarm(alarmEntity.toLocal())
    }
}