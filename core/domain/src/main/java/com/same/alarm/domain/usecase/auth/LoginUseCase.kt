package com.same.alarm.domain.usecase.auth

import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.domain.repository.UserRepository
import com.same.alarm.model.login.LoginRequest
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(kakaoAccessToken: String, loginRequest: LoginRequest): Result<Boolean> {
        return runCatching {
            val login = authRepository.login(
                kakaoAccessToken = kakaoAccessToken,
                loginRequest
            )
            authRepository.saveAccessToken(login.accessToken)
            authRepository.saveRefreshToken(login.refreshToken)

            val userInfo = userRepository.getUserInfo()
            userInfo.isNewUser()
        }
    }
}