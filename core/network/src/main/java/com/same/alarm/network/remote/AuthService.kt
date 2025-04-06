package com.same.alarm.network.remote

import com.same.alarm.network.model.LoginRequestDto
import com.same.alarm.network.model.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/login")
    suspend fun login(
        @Header("Authorization") accessToken: String,
        @Body loginRequestDto: LoginRequestDto
    ): LoginResponseDto
}