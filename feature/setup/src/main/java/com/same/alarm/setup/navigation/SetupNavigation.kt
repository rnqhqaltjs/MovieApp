package com.same.alarm.setup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.navigation.MainTabRoute
import com.same.alarm.navigation.Route
import com.same.alarm.setup.RepeatSetupRoute
import com.same.alarm.setup.SetupRoute

fun NavController.navigateToSetup(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.Setup, navOptions = navOptions)
}

fun NavController.navigateToRepeatSetup(navOptions: NavOptions? = null) {
    navigate(Route.RepeatSetup, navOptions = navOptions)
}

fun NavGraphBuilder.setupScreen(
    onConfirmClick: (List<String>) -> Unit,
    onRepeatClick: () -> Unit
) {
    composable<MainTabRoute.Setup> { navBackStackEntry ->
        SetupRoute(onRepeatClick = onRepeatClick)
    }

    composable<Route.RepeatSetup> { navBackStackEntry ->
        RepeatSetupRoute(onConfirmClick = onConfirmClick)
    }
}