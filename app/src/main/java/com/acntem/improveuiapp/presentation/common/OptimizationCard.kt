package com.acntem.improveuiapp.presentation.common


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acntem.improveuiapp.presentation.ui.theme.dimens

@Composable
fun OptimizationCard(
    modifier: Modifier = Modifier,
    item: OptimizationItem,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .widthIn(
                min = MaterialTheme.dimens.logoSize * 9
            )
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                GradientBox(item.id)
                Text(
                    item.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    item.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp,
                    maxLines = 2
                )
            }

            IconBox(
                icon = Icons.AutoMirrored.Filled.ArrowForward
            )
        }
    }
}

@Composable
fun GradientBox(id: Int) {
    val colors = when (id) {
        1 -> listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.tertiary)
        2 -> listOf(MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.primary)
        else -> listOf(MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.secondary)
    }

    Box(
        modifier = Modifier
            .size(MaterialTheme.dimens.logoSize)
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.linearGradient(colors)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            id.toString(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun IconBox(
    icon: ImageVector,
) {
    Box(
        modifier = Modifier
            .size(MaterialTheme.dimens.logoSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "View details",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}


