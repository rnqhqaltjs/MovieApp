package com.same.alarm.data.mapper

import com.same.alarm.data.model.userinfo.Gender
import com.same.alarm.data.model.userinfo.UserInfoRequestEntity
import com.same.alarm.data.model.userinfo.UserInfoResponseEntity
import com.same.alarm.model.userinfo.UserInfoRequest
import com.same.alarm.model.userinfo.UserInfoResponse

object UserMapper {
    fun UserInfoResponseEntity.toDomain() = UserInfoResponse(
        name = name,
        role = role,
        age = age,
        gender = gender.toDomain(),
        job = job,
        address = address
    )

    private fun Gender.toDomain(): com.same.alarm.model.userinfo.Gender = when (this) {
        Gender.MALE -> com.same.alarm.model.userinfo.Gender.MALE
        Gender.FEMALE -> com.same.alarm.model.userinfo.Gender.FEMALE
    }

    fun UserInfoRequest.toEntity() = UserInfoRequestEntity(
        name = name,
        age = age,
        gender = gender.toEntity(),
        job = job,
        address = address
    )

    private fun com.same.alarm.model.userinfo.Gender.toEntity(): Gender = when (this) {
        com.same.alarm.model.userinfo.Gender.MALE -> Gender.MALE
        com.same.alarm.model.userinfo.Gender.FEMALE -> Gender.FEMALE
    }
}
