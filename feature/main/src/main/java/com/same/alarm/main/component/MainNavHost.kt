package com.same.alarm.main.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.same.alarm.edit.navigation.editNavGraph
import com.same.alarm.feed.navigation.feedNavGraph
import com.same.alarm.list.navigation.listNavGraph
import com.same.alarm.main.MainNavigator
import com.same.alarm.setup.navigation.setupNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    onShowSnackBar: (String) -> Unit,
    padding: PaddingValues,
) {
    NavHost(
        modifier = modifier,
        navController = navigator.navController,
        startDestination = navigator.startDestination,
    ) {
        setupNavGraph(
            onShowSnackBar = onShowSnackBar
        )
        listNavGraph(
            onEditClicked = { navigator.navigateToEdit(it) }
        )
        feedNavGraph()
        editNavGraph(
            onShowSnackBar = onShowSnackBar,
            onNavigateToList = navigator::navigateToList
        )
    }
}