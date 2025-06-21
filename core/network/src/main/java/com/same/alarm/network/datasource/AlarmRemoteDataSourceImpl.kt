package com.same.alarm.network.datasource

import com.same.alarm.data.datasource.AlarmRemoteDataSource
import com.same.alarm.data.model.alarm.AlarmRemoteEntity
import com.same.alarm.network.mapper.AlarmMapper.toDto
import com.same.alarm.network.mapper.AlarmMapper.toEntity
import com.same.alarm.network.model.util.getBodyOrThrow
import com.same.alarm.network.remote.AlarmService
import javax.inject.Inject

class AlarmRemoteDataSourceImpl @Inject constructor(
    private val alarmService: AlarmService
): AlarmRemoteDataSource {
    override suspend fun saveAlarm(alarmRemoteEntity: AlarmRemoteEntity): Long {
        return alarmService.saveAlarm(alarmRemoteEntity.toDto()).getBodyOrThrow()
    }

    override suspend fun deleteAlarm(alarmId: Long) {
        return alarmService.deleteAlarm(alarmId)
    }

    override suspend fun updateAlarm(
        alarmId: Long,
        alarmRemoteEntity: AlarmRemoteEntity
    ): AlarmRemoteEntity {
        val response = alarmService
            .updateAlarm(alarmId, alarmRemoteEntity.toDto())
            .getBodyOrThrow()

        return response.toEntity()
    }

    override suspend fun getAlarmById(alarmId: Long): AlarmRemoteEntity {
        return alarmService.getAlarmById(alarmId).getBodyOrThrow().toEntity()
    }

    override suspend fun getAlarmMessage(): String {
        return alarmService.getAlarmMessage().getBodyOrThrow()
    }
}