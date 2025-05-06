package com.same.alarm.domain.usecase

import com.same.alarm.domain.repository.RingPlayer
import com.same.alarm.domain.repository.VibrationPlayer
import javax.inject.Inject

class StopPlayerUseCase @Inject constructor(
    private val ringPlayer: RingPlayer,
    private val vibrationPlayer: VibrationPlayer
) {
    operator fun invoke() {
        ringPlayer.stop()
        vibrationPlayer.stop()
    }
}