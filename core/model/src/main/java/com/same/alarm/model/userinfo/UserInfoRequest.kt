package com.same.alarm.model.userinfo

data class UserInfoRequest(
    val name: String,
    val age: Int,
    val gender: Gender,
    val job: String,
    val address: String
)
