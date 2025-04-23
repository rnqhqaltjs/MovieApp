package com.same.alarm.setup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.navigation.MainTabRoute
import com.same.alarm.navigation.Route

fun NavController.navigateToSetup(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.Setup, navOptions = navOptions)
}

fun NavController.navigateToSetupDetail(navOptions: NavOptions? = null) {
    navigate(Route.SetupDetail, navOptions = navOptions)
}

fun NavGraphBuilder.setupNavGraph(
    onShowSnackBar: (String) -> Unit
) {
    composable<MainTabRoute.Setup> {
        SetupNavHost (
            onShowSnackBar = onShowSnackBar
        )
    }
}