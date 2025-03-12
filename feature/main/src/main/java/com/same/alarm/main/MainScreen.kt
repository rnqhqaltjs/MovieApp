package com.same.alarm.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.same.alarm.list.ListRoute
import com.same.alarm.main.component.MainBottomBar
import com.same.alarm.main.component.MainNavHost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    MainScreenContent(
        navigator = navigator,
        snackBarHostState = snackBarHostState,
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    snackBarHostState: SnackbarHostState,
    scaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope
) {
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 76.dp,
        sheetDragHandle = {
            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 8.dp)
                    .fillMaxWidth(0.15f)
                    .clickable {
                        coroutineScope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                thickness = 4.dp,
                color = MaterialTheme.colorScheme.outline
            )
        },
        sheetContent = {
            ListRoute()
        }
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
                        .padding(start = 8.dp, end = 8.dp, bottom = 28.dp),
                    visible = navigator.shouldShowBottomBar(),
                    tabs = MainTab.entries,
                    currentTab = navigator.currentTab,
                    onTabSelected = { navigator.navigate(it) }
                )
            },
            snackbarHost = { SnackbarHost(snackBarHostState) }
        )
        BottomSheetHandler(scaffoldState, coroutineScope)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetHandler(
    scaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope
) {
    BackHandler(scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
        coroutineScope.launch {
            scaffoldState.bottomSheetState.partialExpand()
        }
    }
}