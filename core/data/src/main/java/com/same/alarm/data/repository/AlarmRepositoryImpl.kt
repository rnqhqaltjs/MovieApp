package com.same.alarm.data.repository

import com.same.alarm.data.datasource.AlarmDataSource
import com.same.alarm.data.datasource.AlarmHelper
import com.same.alarm.data.mapper.AlarmMapper.toEntity
import com.same.alarm.domain.repository.AlarmRepository
import com.same.alarm.model.Alarm
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDataSource: AlarmDataSource,
    private val alarmHelper: AlarmHelper
) : AlarmRepository {

    override suspend fun addAlarm(alarm: Alarm) {
        alarmHelper.setAlarm(alarm)
//        alarmDataSource.addAlarm(alarm.toEntity())
    }
}