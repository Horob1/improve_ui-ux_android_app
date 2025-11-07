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
    data object LayoutOptimizationScreen: NavScreen()

}
