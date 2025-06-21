package com.same.alarm.network.remote

import com.same.alarm.network.model.util.ApiResponse
import com.same.alarm.network.model.alarm.AlarmDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AlarmService {
    @POST("/alarm")
    suspend fun saveAlarm(@Body alarmDto: AlarmDto): ApiResponse<Long>

    @DELETE("/alarm/{alarmId}")
    suspend fun deleteAlarm(@Path("alarmId") alarmId: Long)

    @PUT("/alarm/{alarmId}")
    suspend fun updateAlarm(
        @Path("alarmId") alarmId: Long,
        @Body alarmDto: AlarmDto
    ): ApiResponse<AlarmDto>

    @GET("/alarm/{alarmId}")
    suspend fun getAlarmById(@Path("alarmId") alarmId: Long): ApiResponse<AlarmDto>

    @GET("/alarm/message")
    suspend fun getAlarmMessage(): ApiResponse<String>
}