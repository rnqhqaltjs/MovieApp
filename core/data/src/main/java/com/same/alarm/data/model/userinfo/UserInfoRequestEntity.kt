package com.same.alarm.data.model.userinfo

data class UserInfoRequestEntity(
    val name: String,
    val age: Int,
    val gender: Gender,
    val job: String,
    val address: String
)