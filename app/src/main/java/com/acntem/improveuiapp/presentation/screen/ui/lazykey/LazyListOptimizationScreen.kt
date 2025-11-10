package com.acntem.improveuiapp.presentation.screen.ui.lazykey

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.acntem.improveuiapp.presentation.common.SimpleSwitchOptimizationLayout
import com.acntem.improveuiapp.presentation.screen.ui.MetricsDashboard

data class Task(val id: Int, val title: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyListOptimizationScreen(
    onPopBackStack: () -> Unit = {}
) {
    var useKeys by remember { mutableStateOf(true) }
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(1, "Task 1"),
                Task(2, "Task 2"),
                Task(3, "Task 3")
            )
        )
    }

    val totalRecompositions = remember { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current

    SimpleSwitchOptimizationLayout(
        title = "LazyList Keys",
        isOptimizeMode = useKeys,
        onPopBackStack = onPopBackStack,
        sharedContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Toggle keys
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Use Keys",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            if (useKeys) "Optimized" else "Not Optimized",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (useKeys)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.error
                        )
                    }
                    Switch(checked = useKeys, onCheckedChange = { useKeys = it })
                }

                Spacer(Modifier.height(16.dp))

                // Shuffle button
                Button(
                    onClick = { tasks = tasks.shuffled() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Shuffle Tasks")
                }

                Spacer(Modifier.height(16.dp))

                // Metrics
                MetricsDashboard(
                    isOptimized = useKeys,
                    itemCount = tasks.size,
                    totalRecompositions = totalRecompositions
                )
            }
        },
        optimizeContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures { focusManager.clearFocus() }
                    },
                contentPadding = PaddingValues(8.dp)
            ) {
                items(
                    items = tasks,
                    key = if (useKeys) { task -> task.id } else null
                ) { task ->
                    TaskItem(task, totalRecompositions)
                }
            }
        },
        nonOptimizeContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures { focusManager.clearFocus() }
                    },
                contentPadding = PaddingValues(8.dp)
            ) {
                items(
                    items = tasks,
                    key = null
                ) { task ->
                    TaskItem(task, totalRecompositions)
                }
            }
        }
    )
}

@Composable
fun TaskItem(
    task: Task,
    recompositionCounter: MutableState<Int>
) {
    SideEffect { recompositionCounter.value++ }

    var notes by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                task.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 3
            )
        }
    }
}
