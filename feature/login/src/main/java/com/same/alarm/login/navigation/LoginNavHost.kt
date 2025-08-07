package com.same.alarm.login.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.same.alarm.navigation.AuthRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun LoginNavHost(
    modifier: Modifier = Modifier,
    navigateToMain: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        NavHost(
            modifier = modifier.padding(padding),
            navController = navController,
            startDestination = AuthRoute.Login,
        ) {
            loginNavGraph(
                onLoginSuccess = { isNewUser ->
                    if (isNewUser) {
                        navController.navigateToUserInfoInput()
                    } else {
                        navigateToMain()
                    }
                },
                onShowSnackBar = { message ->
                    snackbarHostState.showMessage(coroutineScope, message)
                },
            )
            userInfoInputNavGraph(
                onUserInfoInputSuccess = { navigateToMain() },
                onShowSnackBar = { message ->
                    snackbarHostState.showMessage(coroutineScope, message)
                }
            )
        }
    }
}

private fun SnackbarHostState.showMessage(
    coroutineScope: CoroutineScope,
    text: String,
) {
    coroutineScope.launch {
        currentSnackbarData?.dismiss()
        showSnackbar(text)
    }
}