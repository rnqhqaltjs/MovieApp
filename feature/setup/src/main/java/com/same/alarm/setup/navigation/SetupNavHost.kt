package com.same.alarm.setup.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.same.alarm.navigation.MainTabRoute
import com.same.alarm.navigation.Route
import com.same.alarm.setup.SetupDetailRoute
import com.same.alarm.setup.SetupRoute
import com.same.alarm.setup.component.TodayDateHeader

@Composable
fun SetupNavHost(
    onShowSnackBar: (String) -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TodayDateHeader(
                onRefreshClick = {},
                onConfirmClick = {}
            )
        },
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainTabRoute.Setup,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<MainTabRoute.Setup>(
                enterTransition = { slideInVertically { -it } + fadeIn() },
                exitTransition = { slideOutVertically { -it } + fadeOut() }
            ) {
                SetupRoute(
                    onDetailClick = { navController.navigateToSetupDetail() },
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
    }
}