package com.acntem.improveuiapp.presentation.screen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.acntem.improveuiapp.R
import kotlin.system.measureNanoTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutOptimizationScreen() {
    var useOptimized by remember { mutableStateOf(false) }
    var recompositionCount by remember { mutableStateOf(0) }
    var lastRenderTime by remember { mutableStateOf(0L) }

    // Cập nhật đếm mỗi lần recompose
    SideEffect { recompositionCount++ }

    // Mô phỏng phép đo thời gian render
    val renderTime = remember { mutableStateOf(0L) }

    val measured = measureNanoTime {
        if (useOptimized) {
            OptimizedLayout()
        } else {
            MessyNestedLayout()
        }
    }
    renderTime.value = measured / 1_000_000 // chuyển ns → ms
    lastRenderTime = renderTime.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (useOptimized) "✅ Optimized Layout" else "❌ Nested Layout",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Switch(
                        checked = useOptimized,
                        onCheckedChange = { useOptimized = it },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8F9FA))
        ) {
            PerformancePanel(
                recompositionCount = recompositionCount,
                renderTime = lastRenderTime,
                optimized = useOptimized
            )

            Spacer(Modifier.height(8.dp))

            // Danh sách mô phỏng UI phức tạp
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(5) {
                    if (useOptimized)
                        OptimizedItemCard("User #$it")
                    else
                        MessyItemCard("User #$it")
                }
            }
        }
    }
}

@Composable
private fun PerformancePanel(recompositionCount: Int, renderTime: Long, optimized: Boolean) {
    val color = if (optimized) Color(0xFF4CAF50) else Color(0xFFD32F2F)
    val label = if (optimized) "Optimized Mode" else "Nested Layout Mode"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(label, fontWeight = FontWeight.Bold, color = color)
            Spacer(Modifier.height(8.dp))
            Text("Recomposition count: $recompositionCount")
            Text("Render time: ${renderTime} ms")
            Text(
                "⚙️ Tip: Optimized layouts use fewer composition layers → smoother UI",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun MessyNestedLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column {
                Image(
                    painter = painterResource(R.drawable.red_face),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            }
            Column(Modifier.padding(start = 12.dp)) {
                Text("Anna Smith", fontWeight = FontWeight.Bold)
                Text("UX Designer")
                Row {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color.Gray)
                    Text(" Joined 2021", color = Color.Gray)
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Row {
            Column {
                Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.Red)
                Text("Likes: 250")
            }
            Spacer(Modifier.width(40.dp))
            Column {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Blue)
                Text("Followers: 4.3k")
            }
        }
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {}) { Text("Follow") }
        }
    }
}

@Composable
private fun OptimizedLayout() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .height(160.dp)
    ) {
        // Avatar
        Image(
            painter = painterResource(R.drawable.red_face),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .align(Alignment.TopStart)
        )

        // Info
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 76.dp)
        ) {
            Text("Anna Smith", fontWeight = FontWeight.Bold)
            Text("UX Designer")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = null, tint = Color.Gray)
                Text(" Joined 2021", color = Color.Gray)
            }
        }

        // Stats
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart),
            horizontalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Column {
                Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.Red)
                Text("Likes: 250")
            }
            Column {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Blue)
                Text("Followers: 4.3k")
            }
        }

        Button(
            onClick = {},
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text("Follow")
        }
    }
}

@Composable
private fun MessyItemCard(username: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        MessyNestedLayout()
    }
}

@Composable
private fun OptimizedItemCard(username: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        OptimizedLayout()
    }
}
