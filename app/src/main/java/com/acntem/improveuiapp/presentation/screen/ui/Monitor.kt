package com.acntem.improveuiapp.presentation.screen.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay

@Composable
fun FPSMonitor() {
    var fps by remember { mutableStateOf(0f) }
    var frameCount by remember { mutableStateOf(0) }
    var lastTime by remember { mutableStateOf(System.nanoTime()) }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { frameTime ->
                frameCount++
                val elapsed = (frameTime - lastTime) / 1_000_000_000.0

                if (elapsed >= 1.0) {
                    fps = frameCount / elapsed.toFloat()
                    frameCount = 0
                    lastTime = frameTime
                }
            }
        }
    }

    val color = when {
        fps >= 55 -> MaterialTheme.colorScheme.primary
        fps >= 30 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }
    Text(
        text = "${fps.toInt()}",
        color = color,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun MemoryUsageMonitor() {
    var usedMB by remember { mutableStateOf(0L) }

    LaunchedEffect(Unit) {
        while (true) {
            val runtime = Runtime.getRuntime()
            usedMB = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
            delay(500)
        }
    }

    val color = when {
        usedMB < 100 -> MaterialTheme.colorScheme.primary
        usedMB < 200 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }

    Text(
        text = "${usedMB}MB",
        color = color,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium
    )
}