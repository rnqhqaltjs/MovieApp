package com.same.alarm.data.mapper

import com.same.alarm.data.model.AlarmEntity
import com.same.alarm.model.Alarm

object AlarmMapper {
    fun AlarmEntity.toDomain(): Alarm {
        return Alarm(
            id = this.id.toInt(),
            time = this.time,
            statusMessage = this.statusMessage,
            daysOfWeek = this.daysOfWeek,
            category = this.category,
            isRepeating = this.isRepeating,
            isActive = this.isActive,
            isPinned = this.isPinned
        )
    }

    fun Alarm.toEntity(): AlarmEntity {
        return AlarmEntity(
            id = this.id.toLong(),
            time = this.time,
            statusMessage = this.statusMessage,
            daysOfWeek = this.daysOfWeek,
            category = this.category,
            isRepeating = this.isRepeating,
            isActive = this.isActive,
            isPinned = this.isPinned
        )
    }
}