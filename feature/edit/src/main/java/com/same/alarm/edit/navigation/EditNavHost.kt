package com.same.alarm.edit.navigation

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
import com.same.alarm.edit.EditDetailRoute
import com.same.alarm.edit.EditRoute
import com.same.alarm.edit.EditViewModel
import com.same.alarm.edit.component.TodayDateHeader
import com.same.alarm.navigation.EditRoute
import com.same.alarm.navigation.Route

@Composable
fun EditNavHost(
    alarmId: Int,
    onShowSnackBar: (String) -> Unit,
    onNavigateToList: () -> Unit
) {
    val navController = rememberNavController()
    var onConfirmClick: () -> Unit = {}
    var onRefreshClick: () -> Unit = {}

    LaunchedEffect(Unit) {
        if (isSameCurrentDestination<EditRoute.EditDetail>(navController)) {
            navController.navigate(EditRoute.Edit) {
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
                startDestination = EditRoute.Edit(alarmId = alarmId),
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<EditRoute.Edit>(
                    enterTransition = { slideInVertically { -it } + fadeIn() },
                    exitTransition = { slideOutVertically { -it } + fadeOut() }
                ) { backStackEntry ->
                    val editViewModel: EditViewModel = hiltViewModel(backStackEntry)

                    EditRoute(
                        editViewModel = editViewModel,
                        onDetailClick = navController::navigateToEditDetail,
                        onShowSnackBar = onShowSnackBar,
                        onNavigateToList = onNavigateToList
                    )

                    onConfirmClick = editViewModel::updateAlarm
                    onRefreshClick = editViewModel::clearData
                }

                composable<EditRoute.EditDetail>(
                    enterTransition = { slideInVertically { it } + fadeIn() },
                    exitTransition = { slideOutVertically { it } + fadeOut() }
                ) {
                    val editViewModel: EditViewModel =
                        navController.previousBackStackEntry?.let {
                            hiltViewModel(it)
                        } ?: hiltViewModel()

                    EditDetailRoute(
                        editViewModel = editViewModel,
                        onShowSnackBar = onShowSnackBar,
                        onNavigateToList = onNavigateToList
                    )

                    onConfirmClick = editViewModel::updateAlarm
                    onRefreshClick = editViewModel::clearData
                }
            }
        }
    }
}

private inline fun <reified T : Route> isSameCurrentDestination(navController: NavHostController): Boolean {
    return navController.currentDestination?.hasRoute<T>() == true
}