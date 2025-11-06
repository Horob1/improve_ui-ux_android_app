package com.acntem.improveuiapp.presentation.common

import com.acntem.improveuiapp.presentation.navigation.NavScreen

data class OptimizationItem(
    val id: Int,
    val title: String,
    val description: String,
    val navScreen: NavScreen = NavScreen.LayoutOptimizationScreen
)