package com.same.alarm.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data class Edit(val alarmId: Int) : Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Setup : MainTabRoute

    @Serializable
    data object List : MainTabRoute

    @Serializable
    data object Feed : MainTabRoute
}

sealed interface SetupRoute {
    @Serializable
    data object Setup : MainTabRoute

    @Serializable
    data object SetupDetail : Route
}

sealed interface EditRoute {
    @Serializable
    data object Edit : Route

    @Serializable
    data object EditDetail : Route
}


sealed interface AuthRoute {
    @Serializable
    data object Login : AuthRoute

    @Serializable
    data object UserInfoInput : AuthRoute
}