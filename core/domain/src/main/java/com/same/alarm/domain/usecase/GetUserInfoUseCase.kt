package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.UserRepository
import com.same.alarm.model.userinfo.UserInfoResponse
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): UserInfoResponse = userRepository.getUserInfo()
}