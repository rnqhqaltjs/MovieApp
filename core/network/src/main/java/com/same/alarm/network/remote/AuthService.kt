package com.same.alarm.network.remote

import com.same.alarm.network.model.login.LoginRequestDto
import com.same.alarm.network.model.login.LoginResponseDto
import com.same.alarm.network.model.ReissueResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(
        @Header("Authorization") accessToken: String,
        @Body loginRequestDto: LoginRequestDto
    ): LoginResponseDto

    @POST("/auth/reissue")
    suspend fun reissue(@Header("Authorization") refreshToken: String) : ReissueResponseDto

    @POST("/auth/logout")
    suspend fun logout()
}