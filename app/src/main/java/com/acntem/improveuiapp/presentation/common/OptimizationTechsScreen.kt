package com.acntem.improveuiapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.acntem.improveuiapp.presentation.navigation.NavScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptimizationTechsScreen(
    onNavigate: (NavScreen) -> Unit = {},
    icon: ImageVector,
    title: String,
    subtitle: String,
    items: List<OptimizationItem>
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            stickyHeader {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    HeaderSection(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).fillMaxWidth(),
                        icon = icon,
                        title = title,
                        subtitle = subtitle
                    )
                }
            }
            items(items, key = OptimizationItem::id) { item ->
                OptimizationCard(modifier = Modifier.padding(horizontal = 24.dp), item = item) {
                    onNavigate(item.navScreen)
                }
            }
        }
    }
}