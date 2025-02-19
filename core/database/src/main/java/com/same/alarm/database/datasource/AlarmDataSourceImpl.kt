package com.same.alarm.database.datasource

import com.same.alarm.data.datasource.AlarmDataSource
import com.same.alarm.data.model.AlarmEntity
import com.same.alarm.database.room.AlarmDao
import com.same.alarm.database.mapper.AlarmMapper.toLocal
import javax.inject.Inject

class AlarmDataSourceImpl @Inject constructor(
    private val alarmDao: AlarmDao
): AlarmDataSource {

    override suspend fun addAlarm(alarmEntity: AlarmEntity) {
        alarmDao.insertAlarm(alarmEntity.toLocal())
    }
}