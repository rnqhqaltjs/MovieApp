package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(kakaoAccessToken: String): Result<Unit> {
        return runCatching {
            val login = authRepository.login(kakaoToken = kakaoAccessToken)
            authRepository.saveAccessToken(login.accessToken)
        }
    }
}