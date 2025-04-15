package com.same.alarm.network.mapper

import com.same.alarm.data.model.UserInfoResponseEntity
import com.same.alarm.network.model.Gender
import com.same.alarm.network.model.UserInfoResponseDto

object UserMapper {
    fun UserInfoResponseDto.toEntity() = UserInfoResponseEntity(
        name = name ?: "",
        role = role ?: "",
        age = age,
        gender = gender?.toEntity() ?: com.same.alarm.data.model.Gender.MALE,
        job = job ?: "",
        address = address ?: ""
    )

    private fun Gender.toEntity(): com.same.alarm.data.model.Gender = when (this) {
        Gender.MALE -> com.same.alarm.data.model.Gender.MALE
        Gender.FEMALE -> com.same.alarm.data.model.Gender.FEMALE
    }
}