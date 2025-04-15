package com.same.alarm.network.remote

import com.same.alarm.network.model.UserInfoResponseDto
import retrofit2.http.GET

interface UserService {
    @GET("/users/current")
    suspend fun getUserInfo(): UserInfoResponseDto

}