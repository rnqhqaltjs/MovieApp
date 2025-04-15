package com.same.alarm.model

data class UserInfoResponse(
    val name: String,
    val role: String,
    val age: Int,
    val gender: Gender,
    val job: String,
    val address: String
) {
    fun isNewUser(): Boolean {
        return name.isEmpty() || role.isEmpty()
    }
}

enum class Gender {
    MALE, FEMALE
}