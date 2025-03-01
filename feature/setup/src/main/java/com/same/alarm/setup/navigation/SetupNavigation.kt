package com.same.alarm.setup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.same.alarm.navigation.MainTabRoute
import com.same.alarm.navigation.Route
import com.same.alarm.setup.RepeatSetupRoute
import com.same.alarm.setup.SetupRoute

fun NavController.navigateToSetup(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.Setup, navOptions = navOptions)
}

fun NavController.navigateToRepeatSetup(selectedDays: List<Int>, navOptions: NavOptions? = null) {
    navigate(Route.RepeatSetup(selectedDays), navOptions = navOptions)
}

fun NavGraphBuilder.setupScreen(
    onConfirmClick: (List<Int>) -> Unit,
    onRepeatClick: (List<Int>) -> Unit,
) {
    composable<MainTabRoute.Setup> { navBackStackEntry ->
        val restoredSelectedDays = navBackStackEntry.savedStateHandle.get<List<Int>>("days") ?: emptyList()
        SetupRoute(
            onRepeatClick = onRepeatClick,
            restoredSelectedDays = restoredSelectedDays,
        )
    }

    composable<Route.RepeatSetup> { navBackStackEntry ->
        val selectedDays = navBackStackEntry.toRoute<Route.RepeatSetup>().selectedDays
        RepeatSetupRoute(
            onConfirmClick = onConfirmClick,
            restoredSelectedDays = selectedDays
        )
    }
}