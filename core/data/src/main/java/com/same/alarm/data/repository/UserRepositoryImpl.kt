package com.same.alarm.data.repository

import android.content.Context
import android.net.Uri
import com.same.alarm.data.datasource.UserDataSource
import com.same.alarm.data.mapper.UserMapper.toEntity
import com.same.alarm.data.mapper.UserMapper.toDomain
import com.same.alarm.domain.repository.UserRepository
import com.same.alarm.model.userinfo.UserInfoRequest
import com.same.alarm.model.userinfo.UserInfoResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    @ApplicationContext private val context: Context
) : UserRepository {
    override suspend fun getUserInfo(): UserInfoResponse {
        return userDataSource.getUserInfo().toDomain()
    }

    override suspend fun saveUserInfo(userInfoRequest: UserInfoRequest, imageUri: Uri?) {
        val imageFile = imageUri?.let { uriToFile(it, context) }
        return userDataSource.saveUserInfo(userInfoRequest.toEntity(), imageFile)
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        inputStream.use { input ->
            file.outputStream().use { output ->
                input?.copyTo(output)
            }
        }
        return file
    }
}