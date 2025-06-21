package com.same.alarm.data.repository

import com.same.alarm.data.datasource.AlarmLocalDataSource
import com.same.alarm.data.datasource.AlarmRemoteDataSource
import com.same.alarm.data.mapper.AlarmMapper.toDomain
import com.same.alarm.data.mapper.AlarmMapper.toLocalEntity
import com.same.alarm.data.mapper.AlarmMapper.toRemoteEntity
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.alarm.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmLocalDataSource: AlarmLocalDataSource,
    private val alarmRemoteDataSource: AlarmRemoteDataSource
) : AlarmRepository {
    override suspend fun addAlarm(alarm: Alarm): Int {
        val remoteId = alarmRemoteDataSource.saveAlarm(alarm.toRemoteEntity())
        val localId = alarmLocalDataSource.addAlarm(alarm.copy(id = remoteId.toInt()).toLocalEntity())
        return localId.toInt()
    }

    override fun getAllAlarms(): Flow<List<Alarm>> {
        return alarmLocalDataSource.getAllAlarms()
            .map {
                it.map { alarmEntity -> alarmEntity.toDomain() }
            }
    }

    override fun getAlarmById(alarmId: Int): Flow<Alarm?> {
        return alarmLocalDataSource.getAlarmById(alarmId.toLong())
            .map { it?.toDomain() }
    }

    override suspend fun getAlarmMessage(): String {
        return alarmRemoteDataSource.getAlarmMessage()
    }

    override suspend fun removeAlarm(alarmId: Int) {
        alarmLocalDataSource.removeAlarm(alarmId.toLong())
        alarmRemoteDataSource.deleteAlarm(alarmId.toLong())
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        return alarmLocalDataSource.updateAlarm(alarm.toLocalEntity())
    }
}