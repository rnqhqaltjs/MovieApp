package com.same.alarm.ring

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.same.alarm.designsystem.theme.AlarmAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RingActivity : ComponentActivity() {
    private val ringViewModel: RingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        showWhenLockedAndTurnScreenOn()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AlarmAppTheme {
                RingRoute(
                    stopRingCurrent = { stopRingCurrentAndFinish() },
                    stopRingAll = { stopRingAllAndFinish() }
                )
            }
        }
    }

    private fun showWhenLockedAndTurnScreenOn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
    }

    private fun stopRingCurrentAndFinish() {
        ringViewModel.stopRing()
        finishAndRemoveTask()
    }

    private fun stopRingAllAndFinish() {
        ringViewModel.stopRing()
        ringViewModel.cancelTodayAlarms()
        finishAndRemoveTask()
    }
}