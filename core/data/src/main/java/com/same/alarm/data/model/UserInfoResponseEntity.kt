package com.same.alarm.data.model

data class UserInfoResponseEntity(
    val name: String,
    val role: String,
    val age: Int,
    val gender: Gender,
    val job: String,
    val address: String
)

enum class Gender {
    MALE, FEMALE
}