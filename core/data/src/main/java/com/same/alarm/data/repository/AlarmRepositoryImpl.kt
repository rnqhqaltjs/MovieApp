package com.same.alarm.data.repository

import com.same.alarm.data.datasource.AlarmDataSource
import com.same.alarm.data.mapper.AlarmMapper.toDomain
import com.same.alarm.data.mapper.AlarmMapper.toEntity
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDataSource: AlarmDataSource,
) : AlarmRepository {

    override suspend fun addAlarm(alarm: Alarm): Int {
        return alarmDataSource.addAlarm(alarm.toEntity()).toInt()
    }

    override fun getAllAlarms(): Flow<List<Alarm>> {
        return alarmDataSource.getAllAlarms()
            .map {
                it.map { alarmEntity -> alarmEntity.toDomain() }
            }
    }

    override suspend fun getAlarmById(alarmId: Int): Alarm? {
        return alarmDataSource.getAlarmById(alarmId.toLong())?.toDomain()
    }

    override suspend fun removeAlarm(alarm: Alarm) {
        return alarmDataSource.removeAlarm(alarm.toEntity())
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        return alarmDataSource.updateAlarm(alarm.toEntity())
    }
}