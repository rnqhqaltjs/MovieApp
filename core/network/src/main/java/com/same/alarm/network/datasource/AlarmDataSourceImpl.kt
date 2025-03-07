package com.same.alarm.network.datasource

import com.same.alarm.data.datasource.AlarmDataSource
import com.same.alarm.data.model.AlarmEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmDataSourceImpl @Inject constructor(
): AlarmDataSource {
    override suspend fun addAlarm(alarmEntity: AlarmEntity) {
    }

    override fun getAllAlarms(): Flow<List<AlarmEntity>> {
        TODO("Not yet implemented")
    }
}