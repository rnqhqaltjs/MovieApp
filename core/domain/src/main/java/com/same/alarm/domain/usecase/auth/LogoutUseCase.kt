package com.same.alarm.domain.usecase.auth

import com.same.alarm.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Unit = authRepository.logout()
}