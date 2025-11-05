package com.acntem.improveuiapp.presentation.navigation


import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.acntem.improveuiapp.presentation.screen.about.AboutScreen
import com.acntem.improveuiapp.presentation.screen.home.HomeScreen
import com.acntem.improveuiapp.presentation.screen.ui.LayoutOptimizationScreen
import com.acntem.improveuiapp.presentation.screen.ui.UiOptimizationScreen
import com.acntem.improveuiapp.presentation.ui.theme.dimens


@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: NavScreen,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
                )
            ) + fadeIn(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
                )
            ) + fadeOut(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
                )
            ) + fadeIn(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
                )
            ) + fadeOut(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        }
    ) {
        composable<NavScreen.Main> {
            val mainHostController = rememberNavController()
            Scaffold(
                bottomBar = {
                    NavBottomBar(
                        navController = mainHostController
                    )
                }
            ) {
                NavHost(
                    modifier = Modifier.padding(it),
                    navController = mainHostController,
                    startDestination = NavScreen.Home,
                ) {
                    composable<NavScreen.Home> {
                        HomeScreen(
                            onNavigate = { navScreen ->
                                navController.navigate(navScreen)
                            }
                        )
                    }

                    composable<NavScreen.About> {
                        AboutScreen()
                    }


                }
            }
        }
        composable<NavScreen.UiOptimizationScreen> {
            UiOptimizationScreen(
                navController = navController
            )
        }

        composable<NavScreen.LayoutOptimizationScreen> {
            LayoutOptimizationScreen()
        }
    }
}

@Composable
fun NavBottomBar(navController: NavController) {

    val items = remember {
        listOf(
            Triple(NavScreen.Home, "Home", Icons.Filled.Home),
            Triple(NavScreen.About, "About", Icons.Filled.Assistant)
        )
    }

    val currentDestination = navController
        .currentBackStackEntryAsState().value?.destination

    NavigationBar {
        items.forEach { item ->
            val selected = currentDestination?.route == item.first::class.qualifiedName
            val iconSize by animateDpAsState(
                targetValue = if (selected) {
                    MaterialTheme.dimens.logoSize - 4.dp
                } else {
                    MaterialTheme.dimens.logoSize - 16.dp
                },
                animationSpec = tween(
                    durationMillis = 400,
                    easing = FastOutSlowInEasing
                ),
                label = "iconSizeAnimation"
            )

            val iconColor by animateColorAsState(
                targetValue = if (selected)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                ),
                label = "iconColorAnimation"
            )

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.first) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(
                            iconSize
                        ),
                        imageVector = item.third,
                        contentDescription = item.second,
                        tint = iconColor
                    )
                },
            )
        }
    }
}