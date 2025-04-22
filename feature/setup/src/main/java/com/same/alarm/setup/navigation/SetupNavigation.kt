package com.same.alarm.setup.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.navigation.MainTabRoute
import com.same.alarm.navigation.Route
import com.same.alarm.setup.SetupDetailRoute
import com.same.alarm.setup.SetupRoute

fun NavController.navigateToSetup(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.Setup, navOptions = navOptions)
}

fun NavController.navigateToSetupDetail(navOptions: NavOptions? = null) {
    navigate(Route.SetupDetail, navOptions = navOptions)
}

fun NavGraphBuilder.setupNavGraph(
    onDetailClick: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    composable<MainTabRoute.Setup> {
        SetupRoute(
            onDetailClick = onDetailClick,
            onShowSnackBar = onShowSnackBar
        )
    }

    composable<Route.SetupDetail>(
        enterTransition = { slideInVertically { it } + fadeIn() },
        exitTransition = { slideOutVertically { it } + fadeOut() }
    ) {
        SetupDetailRoute()
    }
}