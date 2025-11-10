package com.acntem.improveuiapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.acntem.improveuiapp.presentation.navigation.NavScreen
import com.acntem.improveuiapp.presentation.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptimizationTechsScreen(
    onNavigate: (NavScreen) -> Unit = {},
    icon: ImageVector,
    title: String,
    subtitle: String,
    items: List<OptimizationItem>,
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
        ) {
            stickyHeader {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    HeaderSection(
                        modifier = Modifier
                            .padding(
                                vertical = MaterialTheme.dimens.small1,
                                horizontal = MaterialTheme.dimens.small2
                            )
                            .fillMaxWidth(),
                        icon = icon,
                        title = title,
                        subtitle = subtitle
                    )
                }
            }
            items(items, key = OptimizationItem::id) { item ->
                OptimizationCard(
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.medium1),
                    item = item
                ) {
                    onNavigate(item.navScreen)
                }
            }
        }
    }
}