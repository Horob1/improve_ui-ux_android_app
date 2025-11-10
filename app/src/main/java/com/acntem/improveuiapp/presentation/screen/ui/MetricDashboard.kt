package com.acntem.improveuiapp.presentation.screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MetricsDashboard(isOptimized: Boolean, itemCount: Int, totalRecompositions: MutableState<Int>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MetricItem(label = "FPS") { FPSMonitor() }
                VerticalDivider()
                MetricItem(label = "Memory") { MemoryUsageMonitor() }
                VerticalDivider()
                MetricItem(label = "Total Recomp") {
                    Text(
                        "${totalRecompositions.value}",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                VerticalDivider()
                MetricItem(label = "Items") {
                    Text("$itemCount items", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun VerticalDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier.width(1.dp),
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Composable
fun MetricItem(label: String, content: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(4.dp))
        content()
    }
}