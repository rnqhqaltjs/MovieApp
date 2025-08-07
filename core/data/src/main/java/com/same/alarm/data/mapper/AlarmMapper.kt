package com.same.alarm.data.mapper

import com.same.alarm.data.model.alarm.AlarmLocalEntity
import com.same.alarm.data.model.alarm.AlarmRemoteEntity
import com.same.alarm.model.alarm.Alarm
import java.time.LocalTime

object AlarmMapper {
    fun AlarmLocalEntity.toDomain(): Alarm {
        return Alarm(
            id = this.id.toInt(),
            time = this.time,
            title = this.title,
            statusMessage = this.statusMessage,
            daysOfWeek = this.daysOfWeek,
            category = this.category,
            isRepeating = this.isRepeating,
            isActive = this.isActive,
            isPinned = this.isPinned
        )
    }

    fun AlarmRemoteEntity.toDomain(): Alarm {
        return Alarm(
            time = LocalTime.parse(this.time),
            title = this.title,
            statusMessage = this.statusMessage,
            daysOfWeek = this.daysOfWeek,
            category = this.category,
            isRepeating = this.isRepeating,
            isActive = false,
            isPinned = false
        )
    }

    fun Alarm.toLocalEntity(): AlarmLocalEntity {
        return AlarmLocalEntity(
            id = this.id.toLong(),
            time = this.time,
            title = this.title,
            statusMessage = this.statusMessage,
            daysOfWeek = this.daysOfWeek,
            category = this.category,
            isRepeating = this.isRepeating,
            isActive = this.isActive,
            isPinned = this.isPinned
        )
    }

    fun Alarm.toRemoteEntity(): AlarmRemoteEntity {
        return AlarmRemoteEntity(
            time = this.time.toString(),
            title = this.title,
            statusMessage = this.statusMessage,
            daysOfWeek = this.daysOfWeek,
            category = this.category,
            isRepeating = this.isRepeating,
        )
    }
}