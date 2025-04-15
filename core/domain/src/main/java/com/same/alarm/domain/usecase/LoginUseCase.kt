package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.domain.repository.UserRepository
import com.same.alarm.model.LoginRequest
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

            val userInfo = userRepository.getUserInfo()
            userInfo.isNewUser()
        }
    }
}