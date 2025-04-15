package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.AuthRepository
import com.same.alarm.domain.repository.UserRepository
import com.same.alarm.model.LoginRequest
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Unit = authRepository.logout()
}