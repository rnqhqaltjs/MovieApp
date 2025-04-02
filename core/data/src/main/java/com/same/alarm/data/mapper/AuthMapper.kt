package com.same.alarm.data.mapper

import com.same.alarm.data.model.AlarmEntity
import com.same.alarm.data.model.LoginEntity
import com.same.alarm.model.Alarm
import com.same.alarm.model.Login

object AuthMapper {
    fun LoginEntity.toDomain(): Login {
        return Login(
            accessToken = this.accessToken,
            refreshToken = this.refreshToken
        )
    }

    fun Login.toEntity(): LoginEntity {
        return LoginEntity(
            accessToken = this.accessToken,
            refreshToken = this.refreshToken
        )
    }
}