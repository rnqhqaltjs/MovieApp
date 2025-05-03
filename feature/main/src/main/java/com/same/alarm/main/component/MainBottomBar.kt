package com.same.alarm.main.component

import android.graphics.BlurMaskFilter
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.same.alarm.designsystem.theme.AlarmAppTheme
import com.same.alarm.main.MainTab

@Composable
internal fun MainBottomBar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    tabs: List<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp, start = 15.dp, end = 15.dp)
                .drawBehind {
                    val paint = Paint().asFrameworkPaint().apply {
                        isAntiAlias = true
                        color = "#FF956E".toColorInt()
                        maskFilter = BlurMaskFilter(12f, BlurMaskFilter.Blur.NORMAL)
                    }

                    drawIntoCanvas {
                        val left = 0f
                        val top = 0f
                        val right = size.width
                        val bottom = size.height

                        it.nativeCanvas.drawRoundRect(
                            left,
                            top,
                            right,
                            bottom,
                            47.dp.toPx(),
                            47.dp.toPx(),
                            paint
                        )
                    }
                }
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(47.dp),
                    )
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                tabs.forEach { tab ->
                    MainBottomBarItem(
                        tab = tab,
                        selected = tab == currentTab,
                        onClick = { onTabSelected(tab) },
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
    modifier: Modifier = Modifier,
    tab: MainTab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .weight(1f)
            .fillMaxHeight()
            .selectable(
                selected = selected,
                indication = null,
                role = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(tab.iconResId),
            contentDescription = tab.contentDescription,
            tint = if (selected) Color.Black else Color.Unspecified,
            modifier = Modifier.size(34.dp),
        )
    }
}

@Preview
@Composable
private fun MainBottomBarPreview() {
    AlarmAppTheme {
        MainBottomBar(
            visible = true,
            tabs = MainTab.entries,
            currentTab = MainTab.LIST,
            onTabSelected = {}
        )
    }
}