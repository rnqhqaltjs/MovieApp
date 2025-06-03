package com.same.alarm.domain.usecase.auth

import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TryAutoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return runCatching {
            val token = authRepository.getAccessToken().first()
            if (token.isEmpty()) throw IllegalStateException("No token found")

            val userInfo = userRepository.getUserInfo()
            userInfo.isNewUser()
        }
    }
}