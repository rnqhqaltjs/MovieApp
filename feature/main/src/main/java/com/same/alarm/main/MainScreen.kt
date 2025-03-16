package com.same.alarm.main

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.same.alarm.main.component.MainBottomBar
import com.same.alarm.main.component.MainNavHost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    MainScreenContent(
        navigator = navigator,
        snackBarHostState = snackBarHostState,
        coroutineScope = coroutineScope
    )
}

@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    Scaffold(
        modifier = modifier,
        content = { padding ->
            MainNavHost(
                navigator = navigator,
                onShowSnackBar = { message ->
                    snackBarHostState.showMessage(coroutineScope, message)
                },
                padding = padding
            )
        },
        bottomBar = {
            MainBottomBar(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(start = 8.dp, end = 8.dp),
                visible = navigator.shouldShowBottomBar(),
                tabs = MainTab.entries,
                currentTab = navigator.currentTab,
                onTabSelected = { navigator.navigate(it) },
                onBottomSheetClicked = { navigator.navigateToList() }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    )
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