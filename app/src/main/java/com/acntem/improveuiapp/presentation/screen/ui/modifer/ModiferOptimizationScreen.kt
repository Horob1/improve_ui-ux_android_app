package com.acntem.improveuiapp.presentation.screen.ui.modifer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.acntem.improveuiapp.presentation.common.SimpleSwitchOptimizationLayout

@Composable
fun ModifierOrderOptimizationScreen(
    onPopBackStack: () -> Unit = {},
) {
    var useOptimizedOrder by remember { mutableStateOf(true) }

    SimpleSwitchOptimizationLayout(
        title = "Modifier Order",
        useVerticalScroll = true,
        isOptimizeMode = useOptimizedOrder,
        onPopBackStack = onPopBackStack,
        sharedContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Optimized Order",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Switch(
                        checked = useOptimizedOrder,
                        onCheckedChange = { useOptimizedOrder = it }
                    )
                }
            }
        },
        optimizeContent = {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                OptimizedModifierBox(onClick = { /* Demo click */ })
                OptimizedBackgroundPaddingBox()
            }
        },
        nonOptimizeContent = {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                NonOptimizedModifierBox(onClick = { /* Demo click */ })
                NonOptimizedBackgroundPaddingBox()
            }
        }
    )
}


@Composable
private fun OptimizedModifierBox(onClick: () -> Unit) {
    // Padding → Clickable → Background
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // padding trước
            .clip(MaterialTheme.shapes.large)
            .clickable { onClick() } // clickable sau padding
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        ClickableBoxContent(
            "Optimized Clickable",
            Icons.Default.Check,
            MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun NonOptimizedModifierBox(onClick: () -> Unit) {
    // Clickable → Padding → Background
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // clickable trước padding
            .padding(16.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.errorContainer),
        contentAlignment = Alignment.Center
    ) {
        ClickableBoxContent(
            "Non-Optimized Clickable",
            Icons.Default.Close,
            MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun ClickableBoxContent(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(24.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = color,
            modifier = Modifier.size(64.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}


@Composable
private fun OptimizedBackgroundPaddingBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp)
            .background(Color.Green)
            .border(2.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
    }
}

@Composable
private fun NonOptimizedBackgroundPaddingBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Red)
            .padding(16.dp)
            .border(2.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
    }
}
