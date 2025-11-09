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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Speed
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
import com.acntem.improveuiapp.presentation.common.OptimizationItem
import com.acntem.improveuiapp.presentation.common.OptimizationTechsScreen
import com.acntem.improveuiapp.presentation.screen.about.AboutScreen
import com.acntem.improveuiapp.presentation.screen.home.HomeScreen
import com.acntem.improveuiapp.presentation.screen.ui.LayoutOptimizationScreen
import com.acntem.improveuiapp.presentation.screen.ux.form.UXFormViewModel
import com.acntem.improveuiapp.presentation.screen.ux.form.UxFormScreen
import com.acntem.improveuiapp.presentation.screen.ux.groupbutton.UXGBViewModel
import com.acntem.improveuiapp.presentation.screen.ux.groupbutton.UxGroupButtonScreen
import com.acntem.improveuiapp.presentation.screen.ux.loading.UxLoadingScreen
import com.acntem.improveuiapp.presentation.screen.ux.requestpermission.UxRequestPermissionScreen
import com.acntem.improveuiapp.presentation.screen.ux.safenav.SafeNavViewModel
import com.acntem.improveuiapp.presentation.screen.ux.safenav.UseSafePopBackStack
import com.acntem.improveuiapp.presentation.ui.theme.dimens
import org.koin.compose.viewmodel.koinViewModel


@Suppress("ParamsComparedByRef")
@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: NavScreen,
) {
    NavHost(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
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
                    modifier = Modifier
                        .padding(it),
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
            val items = remember {
                listOf(
                    OptimizationItem(
                        1,
                        "Layout Optimization",
                        "Compare deeply nested layouts vs flat optimized layouts to reduce composition cost."
                    ),
                    OptimizationItem(
                        2,
                        "LazyColumn vs Column",
                        "Compare performance of LazyColumn and Column when rendering large lists."
                    ),
                    OptimizationItem(
                        3,
                        "remember & derivedStateOf",
                        "Avoid unnecessary recompositions by caching and deriving stable states."
                    ),
                    OptimizationItem(
                        4,
                        "Stable vs Unstable Parameters",
                        "Show how passing unstable data classes triggers recomposition unnecessarily."
                    ),
                    OptimizationItem(
                        5,
                        "keys() in Lazy Lists",
                        "Use key() in LazyColumn items to preserve state and avoid full recomposition."
                    ),
                    OptimizationItem(
                        6,
                        "State Hoisting & Snapshot Flow",
                        "Demonstrate moving state upward to reduce recomposition scope."
                    ),
                    OptimizationItem(
                        7,
                        "rememberSaveable",
                        "Compare remember vs rememberSaveable for handling config changes efficiently."
                    ),
                    OptimizationItem(
                        8,
                        "Recomposition Scope Control",
                        "Split UI into smaller composables to isolate recompositions."
                    ),
                    OptimizationItem(
                        9,
                        "SideEffect, LaunchedEffect, DisposableEffect",
                        "Demonstrate correct use of side-effects to avoid redundant executions."
                    ),
                    OptimizationItem(
                        10,
                        "Subcomposition Layouts (SubcomposeLayout)",
                        "Show advanced optimization by composing only visible/needed content."
                    )
                )
            }
            OptimizationTechsScreen(
                onNavigate = { navScreen ->
                    navController.navigate(navScreen)
                },
                icon = Icons.Default.Speed,
                title = "UI Optimization",
                subtitle = "Performance Tips and Best Practice",
                items = items
            )
        }

        composable<NavScreen.UxOptimizationScreen> {
            val items = remember {
                listOf(
                    OptimizationItem(
                        1,
                        "Safe back screen",
                        "Use navigateUp() instead of popBackStack() to safely navigate back to the previous screen.",
                        NavScreen.SafeBackScreen
                    ),
                    OptimizationItem(
                        id = 2,
                        title = "Loading",
                        description = "Provide a visual loading state to keep users engaged during data loading",
                        NavScreen.LoadingScreen
                    ),
                    OptimizationItem(
                        id = 3,
                        title = "Request Permission",
                        description = "A best practice for handling permissions in Compose",
                        NavScreen.RequestPermissionScreen
                    ),
                    OptimizationItem(
                        id = 4,
                        title = "Animated UI",
                        description = "Apply smooth color and motion transitions to button groups for an interactive experience.",
                        NavScreen.GBScreen
                    ),
                    OptimizationItem(
                        id = 5,
                        title = "Form UX Optimization",
                        description = "Apply optimization techniques to improve form interactions for a smoother, more intuitive user experience.",
                        NavScreen.FormScreen
                    )
                )
            }
            OptimizationTechsScreen(
                onNavigate = { navScreen ->
                    navController.navigate(navScreen)
                },
                icon = Icons.Default.Lightbulb,
                title = "UX Optimization",
                subtitle = "Increase UX Tips and Best Practice",
                items = items
            )
        }

        composable<NavScreen.SafeBackScreen> {
            val viewModel = koinViewModel<SafeNavViewModel>()
            UseSafePopBackStack(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable<NavScreen.LoadingScreen> {
            UxLoadingScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<NavScreen.RequestPermissionScreen> {
            UxRequestPermissionScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<NavScreen.GBScreen> {
            val viewModel = koinViewModel<UXGBViewModel>()
            UxGroupButtonScreen(
                onBack = {
                    navController.navigateUp()
                },
                viewModel = viewModel
            )
        }

        composable<NavScreen.FormScreen> {
            val viewModel = koinViewModel<UXFormViewModel>()
            UxFormScreen(
                onBack = {
                    navController.navigateUp()
                },
                viewModel = viewModel
            )
        }

        composable<NavScreen.LayoutOptimizationScreen> {
            LayoutOptimizationScreen(
            )
        }
    }
}

@Suppress("ParamsComparedByRef")
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
                targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
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