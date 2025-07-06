package com.same.alarm.edit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.same.alarm.navigation.EditRoute
import com.same.alarm.navigation.Route

fun NavController.navigateToEdit(route: Route.Edit, navOptions: NavOptions? = null) {
    navigate(route, navOptions = navOptions)
}

fun NavController.navigateToEditDetail(navOptions: NavOptions? = null) {
    navigate(EditRoute.EditDetail, navOptions = navOptions)
}

fun NavGraphBuilder.editNavGraph(
    onShowSnackBar: (String) -> Unit
) {
    composable<Route.Edit> {
        val alarmId = it.toRoute<Route.Edit>().alarmId
        EditNavHost (
            alarmId = alarmId,
            onShowSnackBar = onShowSnackBar
        )
    }
}