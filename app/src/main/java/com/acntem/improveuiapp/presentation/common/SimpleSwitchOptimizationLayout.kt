package com.acntem.improveuiapp.presentation.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSwitchOptimizationLayout(
    title: String =  "UI Optimization 🎶",
    optimizeContent: @Composable () -> Unit = {},
    nonOptimizeContent: @Composable () -> Unit= {},
    onPopBackStack: () -> Unit = {}
) {
    val isOptimized = rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(
                        onClick = onPopBackStack
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Switch(
                        checked = isOptimized.value,
                        onCheckedChange = {
                            isOptimized.value = it
                        }
                    )
                }
            )
        },
    ) { paddingValues ->
        AnimatedContent(
            targetState = isOptimized.value,
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            contentAlignment = Alignment.Center,
            transitionSpec = {
                if (targetState) {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(400)
                    ) + fadeIn(animationSpec = tween(400)) togetherWith
                            slideOutHorizontally(
                                targetOffsetX = { -it / 2 },
                                animationSpec = tween(400)
                            ) + fadeOut(animationSpec = tween(400))
                } else {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(400)
                    ) + fadeIn(animationSpec = tween(400)) togetherWith
                            slideOutHorizontally(
                                targetOffsetX = { it / 2 },
                                animationSpec = tween(400)
                            ) + fadeOut(animationSpec = tween(400))
                }.using(
                    SizeTransform(clip = false)
                )
            }
        ) {state ->
            if (state) {
                optimizeContent()
            } else {
                nonOptimizeContent()
            }
        }
    }
}