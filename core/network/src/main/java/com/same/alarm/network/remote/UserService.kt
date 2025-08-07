package com.same.alarm.network.remote

import com.same.alarm.network.model.userinfo.UserInfoResponseDto
import com.same.alarm.network.model.util.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserService {
    @GET("/user/current")
    suspend fun getUserInfo(): ApiResponse<UserInfoResponseDto>


    @Multipart
    @POST("/user/save")
    suspend fun saveUserInfo(
        @Part("data") userInfoJson: RequestBody,
        @Part image: MultipartBody.Part?
    )
}