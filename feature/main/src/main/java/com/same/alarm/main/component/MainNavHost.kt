package com.same.alarm.main.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.same.alarm.calendar.navigation.calendarScreen
import com.same.alarm.main.MainNavigator
import com.same.alarm.setup.navigation.setupScreen

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues,
) {
    NavHost(
        modifier = modifier,
        navController = navigator.navController,
        startDestination = navigator.startDestination,
    ) {
        setupScreen(
            onConfirmClick = { days ->
                navigator.setDaysResult(days)
                navigator.popBackStackIfNotSetup()
            },
            onRepeatClick = { days ->
                navigator.navigateToRepeatSetup(days)
            },
        )
        calendarScreen()
    }
}