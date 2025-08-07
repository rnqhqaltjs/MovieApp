package com.same.alarm.network.datasource

import com.same.alarm.data.datasource.UserDataSource
import com.same.alarm.data.model.userinfo.UserInfoRequestEntity
import com.same.alarm.data.model.userinfo.UserInfoResponseEntity
import com.same.alarm.network.mapper.UserMapper.toDto
import com.same.alarm.network.mapper.UserMapper.toEntity
import com.same.alarm.network.model.userinfo.UserInfoRequestDto
import com.same.alarm.network.model.util.getBodyOrThrow
import com.same.alarm.network.remote.UserService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService,
) : UserDataSource {
    override suspend fun getUserInfo(): UserInfoResponseEntity {
        return userService.getUserInfo().getBodyOrThrow().toEntity()
    }

    override suspend fun saveUserInfo(userInfoRequestEntity: UserInfoRequestEntity, image: File?) {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val adapter = moshi.adapter(UserInfoRequestDto::class.java)
        val json = adapter.toJson(userInfoRequestEntity.toDto())

        val userInfoRequestBody: RequestBody =
            json.toRequestBody("application/json".toMediaType())

        val imagePart = image?.let {
            val requestFile = it.asRequestBody("image/*".toMediaType())
            MultipartBody.Part.createFormData("image", it.name, requestFile)
        }

        userService.saveUserInfo(userInfoRequestBody, imagePart)
    }
}