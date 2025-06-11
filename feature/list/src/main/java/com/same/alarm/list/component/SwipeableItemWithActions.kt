package com.same.alarm.list.component

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeableItemWithActions(
    isRevealed: Boolean,
    leftActions: @Composable RowScope.() -> Unit,
    rightActions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    onLeftExpanded: () -> Unit = {},
    onRightExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var contextMenuWidth by remember {
        mutableFloatStateOf(0f)
    }
    val offset = remember {
        Animatable(initialValue = 0f)
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = isRevealed, contextMenuWidth) {
        when {
            isRevealed && offset.value >= 0 -> offset.animateTo(contextMenuWidth)
            isRevealed && offset.value < 0  -> offset.animateTo(-contextMenuWidth)
            !isRevealed -> offset.animateTo(0f)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .onSizeChanged { contextMenuWidth = it.width.toFloat() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            leftActions()
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .onSizeChanged { contextMenuWidth = it.width.toFloat() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            rightActions()
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(contextMenuWidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount)
                                    .coerceIn(-contextMenuWidth, contextMenuWidth)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value >= contextMenuWidth / 2f -> {
                                    scope.launch {
                                        offset.animateTo(contextMenuWidth)
                                        onRightExpanded()
                                    }
                                }

                                offset.value <= -contextMenuWidth / 2f -> {
                                    scope.launch {
                                        offset.animateTo(-contextMenuWidth)
                                        onLeftExpanded()
                                    }
                                }

                                else -> {
                                    scope.launch {
                                        offset.animateTo(0f)
                                        onCollapsed()
                                    }
                                }
                            }
                        },
                    )
                }
        ) {
            content()
        }
    }
}