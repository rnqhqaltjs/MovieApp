package com.same.alarm.network.mapper

import com.same.alarm.data.model.alarm.AlarmRemoteEntity
import com.same.alarm.network.model.alarm.AlarmDto

object AlarmMapper {
    fun AlarmRemoteEntity.toDto(): AlarmDto =
        AlarmDto(
            title = title,
            statusMessage = statusMessage,
            category = category,
            time = time,
            daysOfWeek = daysOfWeek,
            isRepeating = isRepeating
        )

    fun AlarmDto.toEntity(): AlarmRemoteEntity =
        AlarmRemoteEntity(
            time = time,
            title = title,
            statusMessage = statusMessage,
            daysOfWeek = daysOfWeek,
            category = category,
            isRepeating = isRepeating
        )
}