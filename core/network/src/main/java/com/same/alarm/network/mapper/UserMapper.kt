package com.same.alarm.network.mapper

import com.same.alarm.data.model.userinfo.UserInfoRequestEntity
import com.same.alarm.data.model.userinfo.UserInfoResponseEntity
import com.same.alarm.network.model.userinfo.Gender
import com.same.alarm.network.model.userinfo.UserInfoRequestDto
import com.same.alarm.network.model.userinfo.UserInfoResponseDto

object UserMapper {
    fun UserInfoResponseDto.toEntity() = UserInfoResponseEntity(
        name = name,
        role = role,
        age = age ?: -1,
        gender = gender?.toEntity() ?: com.same.alarm.data.model.userinfo.Gender.MALE,
        job = job ?: "",
        address = address ?: ""
    )

    private fun Gender.toEntity(): com.same.alarm.data.model.userinfo.Gender = when (this) {
        Gender.MALE -> com.same.alarm.data.model.userinfo.Gender.MALE
        Gender.FEMALE -> com.same.alarm.data.model.userinfo.Gender.FEMALE
    }

    fun UserInfoRequestEntity.toDto() = UserInfoRequestDto(
        name = name,
        age = age,
        gender = gender.toDto(),
        job = job,
        address = address
    )

    private fun com.same.alarm.data.model.userinfo.Gender.toDto(): Gender = when (this) {
        com.same.alarm.data.model.userinfo.Gender.MALE -> Gender.MALE
        com.same.alarm.data.model.userinfo.Gender.FEMALE -> Gender.FEMALE
    }
}