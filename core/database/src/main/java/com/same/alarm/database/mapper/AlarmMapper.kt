package com.same.alarm.database.mapper

import com.same.alarm.data.model.AlarmEntity
import com.same.alarm.database.model.AlarmLocal

object AlarmMapper {

    fun AlarmLocal.toEntity(): AlarmEntity {
        return AlarmEntity(
            id = this.id,
            time = this.time,
            title = this.title,
            daysOfWeek = this.daysOfWeek
        )
    }

    fun AlarmEntity.toLocal(): AlarmLocal {
        return AlarmLocal(
            id = this.id,
            time = this.time,
            title = this.title,
            daysOfWeek = this.daysOfWeek
        )
    }
}