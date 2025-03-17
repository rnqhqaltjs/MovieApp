package com.same.alarm.setup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.navigation.MainTabRoute
import com.same.alarm.setup.SetupRoute

fun NavController.navigateToSetup(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.Setup, navOptions = navOptions)
}

fun NavGraphBuilder.setupNavGraph(
    onShowSnackBar: (String) -> Unit
) {
    composable<MainTabRoute.Setup> {
        SetupRoute(onShowSnackBar = onShowSnackBar)
    }
}