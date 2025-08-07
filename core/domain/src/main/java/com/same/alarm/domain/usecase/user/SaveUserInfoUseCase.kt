package com.same.alarm.domain.usecase.user

import android.net.Uri
import com.same.alarm.domain.repository.UserRepository
import com.same.alarm.model.userinfo.UserInfoRequest
import java.io.File
import javax.inject.Inject

class SaveUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userInfoRequest: UserInfoRequest,
        image: Uri?
    ): Result<Unit> {
        return runCatching {
            userRepository.saveUserInfo(userInfoRequest, image)
        }
    }
}