package com.acntem.improveuiapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.acntem.improveuiapp.presentation.navigation.NavScreen
import com.acntem.improveuiapp.presentation.navigation.SetupNavGraph
import com.acntem.improveuiapp.presentation.ui.theme.ImproveUIAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            ImproveUIAppTheme {
                SetupNavGraph(
                    startDestination = NavScreen.Main,
                    navController = rememberNavController()
                )
            }
        }
    }
}