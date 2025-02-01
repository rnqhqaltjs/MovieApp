package com.same.alarm.setup.navigation

import SetupRoute
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.navigation.MainTabRoute

fun NavController.navigateToSetup(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.Setup, navOptions = navOptions)
}

fun NavGraphBuilder.setupScreen() {
    composable<MainTabRoute.Setup> { _ ->
        SetupRoute()
    }
}