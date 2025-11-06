package com.acntem.improveuiapp.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSwitchOptimizationLayout(
    title: String =  "UI Optimization 🎶",
    optimizeContent: @Composable BoxScope.() -> Unit = {},
    nonOptimizeContent: @Composable BoxScope.() -> Unit= {},
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
    ) {
        Box(
            Modifier.padding(it).fillMaxSize()
        ){
            if (isOptimized.value) {
                optimizeContent()
            } else {
                nonOptimizeContent()
            }
        }
    }
}