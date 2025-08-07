package com.same.alarm.network.remote

import com.same.alarm.network.model.util.ApiResponse
import com.same.alarm.network.model.auth.LoginRequestDto
import com.same.alarm.network.model.auth.LoginResponseDto
import com.same.alarm.network.model.auth.ReissueResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(
        @Header("Authorization") accessToken: String,
        @Body loginRequestDto: LoginRequestDto
    ):  ApiResponse<LoginResponseDto>

    @POST("/auth/reissue")
    suspend fun reissue(@Header("Authorization") refreshToken: String) : ApiResponse<ReissueResponseDto>

    @POST("/auth/logout")
    suspend fun logout()
}