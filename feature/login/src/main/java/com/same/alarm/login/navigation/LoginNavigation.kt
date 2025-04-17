package com.same.alarm.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.same.alarm.login.LoginRoute
import com.same.alarm.login.UserInfoInputRoute
import com.same.alarm.navigation.AuthRoute

fun NavController.navigateToLogin(navOptions: NavOptions) {
    navigate(AuthRoute.Login, navOptions = navOptions)
}

fun NavController.navigateToUserInfoInput() {
    navigate(AuthRoute.UserInfoInput) {
        popUpTo(AuthRoute.Login) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.loginNavGraph(
    onLoginSuccess: (Boolean) -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    composable<AuthRoute.Login> {
        LoginRoute(
            onLoginSuccess = onLoginSuccess,
            onShowSnackBar = onShowSnackBar
        )
    }
}

fun NavGraphBuilder.userInfoInputNavGraph(
    onUserInfoInputSuccess: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    composable<AuthRoute.UserInfoInput> {
        UserInfoInputRoute(
            onUserInfoInputSuccess = onUserInfoInputSuccess,
            onShowSnackBar = onShowSnackBar
        )
    }
}