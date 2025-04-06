package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.model.LoginRequest
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(kakaoAccessToken: String, loginRequest: LoginRequest): Result<Boolean> {
        return runCatching {
            val login = authRepository.login(
                kakaoAccessToken = kakaoAccessToken,
                loginRequest
            )
            authRepository.saveAccessToken(login.accessToken)
            true
        }
    }
}