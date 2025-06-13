package com.same.alarm.network.datasource

import com.same.alarm.data.datasource.AlarmRemoteDataSource
import com.same.alarm.data.model.alarm.AlarmRemoteEntity
import com.same.alarm.network.mapper.AlarmMapper.toDto
import com.same.alarm.network.mapper.AlarmMapper.toEntity
import com.same.alarm.network.remote.AlarmService
import javax.inject.Inject

class AlarmRemoteDataSourceImpl @Inject constructor(
    private val alarmService: AlarmService
): AlarmRemoteDataSource {
    override suspend fun saveAlarm(alarmRemoteEntity: AlarmRemoteEntity) {
        return alarmService.saveAlarm(alarmRemoteEntity.toDto())
    }

    override suspend fun deleteAlarm(alarmId: Long) {
        return alarmService.deleteAlarm(alarmId)
    }

    override suspend fun updateAlarm(
        alarmId: Long,
        alarmRemoteEntity: AlarmRemoteEntity
    ) {
        return alarmService.updateAlarm(alarmId, alarmRemoteEntity.toDto())
    }

    override suspend fun getAlarmById(alarmId: Long): AlarmRemoteEntity {
        return alarmService.getAlarmById(alarmId).toEntity()
    }
}