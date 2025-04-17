package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.UserRepository
import com.same.alarm.model.userinfo.UserInfoRequest
import javax.inject.Inject

class SaveUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userInfoRequest: UserInfoRequest): Result<Unit> {
        return runCatching {
            validate(userInfoRequest)
            userRepository.saveUserInfo(userInfoRequest)
        }
    }

    private fun validate(user: UserInfoRequest) {
        when {
            user.name.isBlank() -> throw IllegalArgumentException("이름을 입력해주세요.")
            user.age <= 0 -> throw IllegalArgumentException("나이를 올바르게 입력해주세요.")
            user.job.isBlank() -> throw IllegalArgumentException("직업을 입력해주세요.")
            user.address.isBlank() -> throw IllegalArgumentException("거주지를 입력해주세요.")
        }
    }
}