package com.same.alarm.data.mapper

import com.same.alarm.data.model.AlarmEntity
import com.same.alarm.model.Alarm

object AlarmMapper {

    fun AlarmEntity.toDomain(): Alarm {
        return Alarm(
            id = this.id,
            time = this.time,
            title = this.title,
            daysOfWeek = this.daysOfWeek
        )
    }

    fun Alarm.toEntity(): AlarmEntity {
        return AlarmEntity(
            id = this.id,
            time = this.time,
            title = this.title,
            daysOfWeek = this.daysOfWeek
        )
    }
}