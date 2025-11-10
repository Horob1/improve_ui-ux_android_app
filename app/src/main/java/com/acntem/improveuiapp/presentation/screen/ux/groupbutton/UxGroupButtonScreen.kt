package com.acntem.improveuiapp.presentation.screen.ux.groupbutton

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acntem.improveuiapp.domain.model.BackgroundTheme
import com.acntem.improveuiapp.presentation.ui.theme.dimens


@Composable
fun getBackgroundColor(theme: BackgroundTheme): Color {
    return when (theme) {
        BackgroundTheme.Dark -> Color(0xFF121212)
        BackgroundTheme.Medium -> Color(0xFFFA027F)
        BackgroundTheme.Accent -> Color(0xFF1E3A8A)
    }
}

@Composable
fun UxGroupButtonScreen(
    onBack: () -> Unit = {},
    viewModel: UXGBViewModel,
) {
    val colorList = remember {
        listOf(
            BackgroundTheme.Dark,
            BackgroundTheme.Medium,
            BackgroundTheme.Accent
        )
    }
    val isLoading by viewModel.isLoading.collectAsState()
    val state by viewModel.state.collectAsState()

    val backgroundColor by animateColorAsState(
        targetValue = getBackgroundColor(state.backgroundTheme).copy(
            alpha = 0.5f
        ),
        animationSpec = tween(300),
        label = "bg"
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = MaterialTheme.dimens.small3,
                            topEnd = MaterialTheme.dimens.small3
                        )
                    )
                    .background(
                        MaterialTheme.colorScheme.surface
                    )
                    .padding(MaterialTheme.dimens.small1),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                    )
                }

                Text(
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small1),
                    text = "Group Button",
                    style = MaterialTheme.typography.titleLarge
                )

                Switch(
                    checked = state.mode,
                    onCheckedChange = {
                        viewModel.setOptimizeGBScreen(
                            !state.mode
                        )
                    }
                )

            }
        }
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (state.mode) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                MaterialTheme.dimens.medium1
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1)
                    ) {
                        items(
                            count = colorList.size,
                            key = { index -> colorList[index].name },
                        ) {
                            ItemButton(
                                colorName = colorList[it].name,
                                isSelected = colorList[it] == state.backgroundTheme,
                                onClick = {
                                    viewModel.setGBColor(colorList[it])
                                },
                                selectedColor = if (colorList[it] == BackgroundTheme.Dark) Color.Gray else getBackgroundColor(
                                    state.backgroundTheme
                                )
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            getBackgroundColor(
                                state.backgroundTheme
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                MaterialTheme.dimens.medium1
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1)
                    ) {
                        items(
                            count = colorList.size,
                            key = { index -> colorList[index].name },
                        ) {
                            val item = colorList[it]
                            val isSelected = item == state.backgroundTheme

                            Button(
                                onClick = { viewModel.setGBColor(item) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) {
                                        if (item == BackgroundTheme.Dark) Color.Gray
                                        else getBackgroundColor(item)
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant
                                    },
                                    contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .defaultMinSize(minHeight = 40.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(text = item.name)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ItemButton(
    modifier: Modifier = Modifier,
    colorName: String = "Blue",
    isSelected: Boolean = false,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedColor: Color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
    onClick: () -> Unit = {},
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.03f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            selectedColor
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        animationSpec = tween(300),
        label = "bg"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .shadow(
                elevation = if (isSelected) 8.dp else 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 8.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(MaterialTheme.dimens.small2)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = colorName,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp)
            )

            // Checkbox Icon
            Icon(
                imageVector = if (isSelected) {
                    Icons.Outlined.CheckCircle
                } else {
                    Icons.Outlined.Circle
                },
                contentDescription = if (isSelected) "Selected" else "Not Selected",
                tint = if (isSelected) selectedColor else unSelectedColor,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
