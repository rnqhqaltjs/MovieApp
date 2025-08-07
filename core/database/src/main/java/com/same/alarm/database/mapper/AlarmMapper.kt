package com.same.alarm.database.mapper

import com.same.alarm.data.model.alarm.AlarmLocalEntity
import com.same.alarm.database.model.AlarmLocal

object AlarmMapper {
    fun AlarmLocal.toEntity(): AlarmLocalEntity {
        return AlarmLocalEntity(
            id = this.id,
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

    fun AlarmLocalEntity.toLocal(): AlarmLocal {
        return AlarmLocal(
            id = this.id,
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
}