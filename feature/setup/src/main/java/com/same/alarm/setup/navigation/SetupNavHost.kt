package com.same.alarm.setup.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.same.alarm.navigation.Route
import com.same.alarm.navigation.SetupRoute
import com.same.alarm.setup.SetupDetailRoute
import com.same.alarm.setup.SetupRoute
import com.same.alarm.setup.SetupViewModel
import com.same.alarm.setup.component.TodayDateHeader

@Composable
fun SetupNavHost(
    onShowSnackBar: (String) -> Unit
) {
    val navController = rememberNavController()
    var onConfirmClick: () -> Unit = {}
    var onRefreshClick: () -> Unit = {}

    LaunchedEffect(Unit) {
        if (isSameCurrentDestination<SetupRoute.SetupDetail>(navController)) {
            navController.navigate(SetupRoute.Setup) {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(255, 106, 51, 204),
                        Color(255, 211, 115, 204)
                    )
                )
            )
    ) {
        Scaffold(
            topBar = {
                TodayDateHeader(
                    onRefreshClick = { onRefreshClick() },
                    onConfirmClick = { onConfirmClick() }
                )
            },
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding(),
            containerColor = Color.Transparent
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = SetupRoute.Setup,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<SetupRoute.Setup>(
                    enterTransition = { slideInVertically { -it } + fadeIn() },
                    exitTransition = { slideOutVertically { -it } + fadeOut() }
                ) { backStackEntry ->
                    val setupViewModel: SetupViewModel = hiltViewModel(backStackEntry)

                    SetupRoute(
                        setupViewModel = setupViewModel,
                        onDetailClick = navController::navigateToSetupDetail,
                        onShowSnackBar = onShowSnackBar
                    )

                    onConfirmClick = setupViewModel::addAlarm
                    onRefreshClick = setupViewModel::clearData
                }

                composable<SetupRoute.SetupDetail>(
                    enterTransition = { slideInVertically { it } + fadeIn() },
                    exitTransition = { slideOutVertically { it } + fadeOut() }
                ) {
                    val setupViewModel: SetupViewModel =
                        navController.previousBackStackEntry?.let {
                            hiltViewModel(it)
                        } ?: hiltViewModel()

                    SetupDetailRoute(
                        setupViewModel = setupViewModel,
                        onShowSnackBar = onShowSnackBar
                    )

                    onConfirmClick = setupViewModel::addAlarm
                    onRefreshClick = setupViewModel::clearData
                }
            }
        }
    }
}

private inline fun <reified T : Route> isSameCurrentDestination(navController: NavHostController): Boolean {
    return navController.currentDestination?.hasRoute<T>() == true
}