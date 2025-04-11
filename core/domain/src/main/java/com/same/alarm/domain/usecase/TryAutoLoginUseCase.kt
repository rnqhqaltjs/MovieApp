package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TryAutoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return runCatching {
            val token = authRepository.getAccessToken().first()
            token.isNotEmpty()
        }
    }
}