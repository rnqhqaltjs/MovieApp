package com.same.alarm.model.userinfo

data class UserInfoResponse(
    val name: String,
    val role: String,
    val age: Int,
    val gender: Gender,
    val job: String,
    val address: String
) {
    fun isNewUser(): Boolean {
        return age == -1
    }
}
