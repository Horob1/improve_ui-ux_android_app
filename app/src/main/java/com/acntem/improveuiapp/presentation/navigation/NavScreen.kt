package com.acntem.improveuiapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavScreen {
    @Serializable
    data object Main : NavScreen()

    @Serializable
    data object Home : NavScreen()

    @Serializable
    data object About : NavScreen()

    @Serializable
    data object UiOptimizationScreen : NavScreen()

    @Serializable
    data object UxOptimizationScreen : NavScreen()

    @Serializable
    data object SafeBackScreen : NavScreen()

    @Serializable
    data object LoadingScreen : NavScreen()

    @Serializable
    data object RequestPermissionScreen : NavScreen()

    @Serializable
    data object GBScreen : NavScreen()

    @Serializable
    data object FormScreen : NavScreen()

    @Serializable
    data object LayoutOptimizationScreen : NavScreen()

}
