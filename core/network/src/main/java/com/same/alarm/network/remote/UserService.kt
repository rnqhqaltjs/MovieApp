package com.same.alarm.network.remote

import com.same.alarm.network.model.util.ApiResponse
import com.same.alarm.network.model.userinfo.UserInfoRequestDto
import com.same.alarm.network.model.userinfo.UserInfoResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("/users/current")
    suspend fun getUserInfo(): ApiResponse<UserInfoResponseDto>

    @POST("/users/save")
    suspend fun saveUserInfo(@Body userInfoRequestDto: UserInfoRequestDto)
}