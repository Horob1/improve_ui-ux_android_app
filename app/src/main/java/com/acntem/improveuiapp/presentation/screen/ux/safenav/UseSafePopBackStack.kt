package com.acntem.improveuiapp.presentation.screen.ux.safenav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.acntem.improveuiapp.presentation.common.SimpleSwitchOptimizationLayout
import com.acntem.improveuiapp.presentation.ui.theme.dimens

@Composable
fun UseSafePopBackStack(
    navController: NavController,
    viewModel: SafeNavViewModel,
) {
    val state by viewModel.state.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val lastTimeClicked = remember {
        mutableStateOf(System.currentTimeMillis())
    }
    val times = remember {
        listOf(
            1,
            5,
            10
        )
    }
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else
        SimpleSwitchOptimizationLayout(
            title = "Safe Back",
            isOptimizeMode = state.optimizeMode,
            onPopBackStack = {
                navController.navigateUp()
            },
            nonOptimizeContent = {
                NavBox(
                    onClick = {
                        if (state.safeBackMode) {
                            if (System.currentTimeMillis() - lastTimeClicked.value > 1000) {
                                navController.popBackStack()
                                lastTimeClicked.value = System.currentTimeMillis()
                            }
                        } else repeat(
                            if (!times.contains(state.repeatCount)) 1 else state.repeatCount
                        ) {
                            navController.popBackStack()
                        }
                    },
                    text = "Non safe back"
                )
            },
            optimizeContent = {
                NavBox(
                    onClick = {
                        if (state.safeBackMode) {
                            if (System.currentTimeMillis() - lastTimeClicked.value > 1000) {
                                navController.navigateUp()
                                lastTimeClicked.value = System.currentTimeMillis()
                            }
                        } else repeat(
                            if (!times.contains(state.repeatCount)) 1 else state.repeatCount
                        ) {
                            navController.navigateUp()
                        }
                    },
                    text = "Safe back"
                )
            },
            sharedContent = {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            MaterialTheme.dimens.medium2
                        ),
                    elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.dimens.medium1)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium3)
                    ) {
                        // Optimize Mode Switch
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Optimize Mode",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Switch(
                                checked = state.optimizeMode,
                                onCheckedChange = {
                                    viewModel.saveSafeNavState(
                                        state.copy(
                                            optimizeMode = it
                                        )
                                    )
                                }
                            )
                        }

                        HorizontalDivider()

                        // Limit Time Clicker Mode Switch
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Limit Time Clicker Mode",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Switch(
                                checked = state.safeBackMode,
                                onCheckedChange = {
                                    viewModel.saveSafeNavState(
                                        state.copy(
                                            safeBackMode = it
                                        )
                                    )
                                }
                            )
                        }

                        // Show Limit Time input when enabled
                        AnimatedVisibility(visible = !state.safeBackMode) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium3)
                            ) {
                                Text(
                                    text = "Repeat Count: ${if (!times.contains(state.repeatCount)) 1 else state.repeatCount}",
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    items(times.size, key = { index ->
                                        times[index]
                                    }) { index ->
                                        Button(
                                            onClick = {
                                                viewModel.saveSafeNavState(
                                                    state.copy(
                                                        repeatCount = times[index],
                                                    )
                                                )
                                            }
                                        ) {
                                            Text(
                                                text = times[index].toString()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
}

@Composable
fun NavBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = modifier,
            onClick = onClick,
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}