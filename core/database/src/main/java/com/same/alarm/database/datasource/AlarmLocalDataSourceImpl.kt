package com.same.alarm.database.datasource

import com.same.alarm.data.datasource.AlarmLocalDataSource
import com.same.alarm.data.model.alarm.AlarmLocalEntity
import com.same.alarm.database.mapper.AlarmMapper.toEntity
import com.same.alarm.database.mapper.AlarmMapper.toLocal
import com.same.alarm.database.room.AlarmDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmLocalDataSourceImpl @Inject constructor(
    private val alarmDao: AlarmDao
): AlarmLocalDataSource {

    override suspend fun addAlarm(alarmLocalEntity: AlarmLocalEntity): Long {
        return alarmDao.insertAlarm(alarmLocalEntity.toLocal())
    }

    override suspend fun removeAlarm(alarmId: Long) {
        return alarmDao.deleteAlarm(alarmId)
    }

    override suspend fun updateAlarm(alarmLocalEntity: AlarmLocalEntity) {
        return alarmDao.updateAlarm(alarmLocalEntity.toLocal())
    }

    override fun getAllAlarms(): Flow<List<AlarmLocalEntity>> {
        return alarmDao.getAllAlarms()
            .map {
                it.map { alarmLocal -> alarmLocal.toEntity() }
            }
    }

    override fun getAlarmById(alarmId: Long): Flow<AlarmLocalEntity?> {
        return alarmDao.getAlarmById(alarmId)
            .map { it?.toEntity() }
    }
}