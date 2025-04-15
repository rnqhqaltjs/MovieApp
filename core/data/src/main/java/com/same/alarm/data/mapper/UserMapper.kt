package com.same.alarm.data.mapper

import com.same.alarm.data.model.Gender
import com.same.alarm.data.model.UserInfoResponseEntity
import com.same.alarm.model.UserInfoResponse

object UserMapper {
    fun UserInfoResponseEntity.toModel() = UserInfoResponse(
        name = name,
        role = role,
        age = age,
        gender = gender.toModel(),
        job = job,
        address = address
    )

    private fun Gender.toModel(): com.same.alarm.model.Gender = when (this) {
        Gender.MALE -> com.same.alarm.model.Gender.MALE
        Gender.FEMALE -> com.same.alarm.model.Gender.FEMALE
    }
}