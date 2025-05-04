package com.same.alarm.ring

import androidx.lifecycle.ViewModel
import com.same.alarm.domain.repository.RingPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RingViewModel @Inject constructor(
    private val ringPlayer: RingPlayer
) : ViewModel() {

    fun startRing() {
        ringPlayer.playRing()
    }

    fun stopRing() {
        ringPlayer.stopRing()
    }
}