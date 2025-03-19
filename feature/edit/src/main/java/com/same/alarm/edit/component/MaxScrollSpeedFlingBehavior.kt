package com.same.alarm.edit.component

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlin.math.abs

@Composable
fun maxScrollSpeedFlingBehavior(): FlingBehavior {
    val splineBasedDecay = rememberSplineBasedDecay<Float>()
    return remember(splineBasedDecay) {
        MaxScrollSpeedFlingBehavior(splineBasedDecay)
    }
}

private class MaxScrollSpeedFlingBehavior(
    private val splineBasedDecay: DecayAnimationSpec<Float>,
) : FlingBehavior {
    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        val setVelocity = if (initialVelocity > 0F) initialVelocity.coerceAtMost(2_000F)
        else initialVelocity.coerceAtLeast(-2_000F)

        return if (abs(setVelocity) > 0f) {
            var velocityLeft = setVelocity
            var lastValue = 0f
            AnimationState(
                initialValue = 0f,
                initialVelocity = setVelocity,
            ).animateDecay(splineBasedDecay) {
                val delta = value - lastValue
                val consumed = scrollBy(delta)
                lastValue = value
                velocityLeft = this.velocity
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
            velocityLeft
        } else setVelocity
    }
}