package com.same.alarm.list.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.same.alarm.designsystem.noRippleClickable

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    width: Dp = 49.dp,
    height: Dp = 24.dp,
    thumbSize: Dp = 20.dp,
    activeTrackColor: Color = Color(0xFFFF956E),
    inactiveTrackColor: Color = Color(0xFFC8C8C8),
    thumbColor: Color = Color.White,
    animationDuration: Int = 200,
    modifier: Modifier = Modifier
) {
    val trackColor by animateColorAsState(
        targetValue = if (checked) activeTrackColor else inactiveTrackColor,
        animationSpec = tween(durationMillis = animationDuration)
    )

    val thumbOffset by animateDpAsState(
        targetValue = if (checked) (height - thumbSize) / 2 else (width - thumbSize - (height - thumbSize) / 2),
        animationSpec = tween(durationMillis = animationDuration)
    )

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(height / 2))
            .background(trackColor)
            .noRippleClickable { onCheckedChange(!checked) }
    ) {
        Box(
            modifier = Modifier
                .size(thumbSize)
                .offset(x = thumbOffset)
                .align(Alignment.CenterStart)
                .clip(CircleShape)
                .background(thumbColor)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomSwitchPreview() {
    CustomSwitch(
        checked = true,
        onCheckedChange = {}
    )
}