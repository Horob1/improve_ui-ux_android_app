package com.acntem.improveuiapp.presentation.screen.ux.loading

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.acntem.improveuiapp.R
import com.acntem.improveuiapp.presentation.ui.theme.dimens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Hiệu ứng loading
// Hiệu ứng skeleton
@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun UxLoadingScreen(
    onBack: () -> Unit = {},
) {

    val isLoading = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(R.drawable.blue_face) // 👈 ID resource
            .build()
    )

    suspend fun startLoading() {
        isLoading.value = true
        delay(5000)
        isLoading.value = false
    }

    LaunchedEffect(Unit) {
        startLoading()
    }



    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(text = "Loading")
                },
                actions = {
                    Button(
                        enabled = !isLoading.value,
                        onClick = {
                            scope.launch {
                                startLoading()
                            }
                        }
                    ) {
                        Text(text = "Reload")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            var currentMode by remember { mutableStateOf("Loading") }

            Card(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.medium1)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(MaterialTheme.dimens.medium1),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(MaterialTheme.dimens.medium1),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Current Mode: $currentMode",
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Spacer(Modifier.height(MaterialTheme.dimens.medium1))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.medium1)
                    ) {
                        val loadingColor by animateColorAsState(
                            targetValue = if (currentMode == "Loading")
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surfaceVariant,
                            animationSpec = tween(300)
                        )

                        val skeletonColor by animateColorAsState(
                            targetValue = if (currentMode == "Skeleton")
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surfaceVariant,
                            animationSpec = tween(300)
                        )

                        Button(
                            onClick = {
                                currentMode = "Loading"
                                scope.launch { startLoading() }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = loadingColor)
                        ) {
                            Text(
                                "Loading",
                                color = if (currentMode == "Loading")
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Button(
                            onClick = {
                                currentMode = "Skeleton"
                                scope.launch { startLoading() }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = skeletonColor)
                        ) {
                            Text(
                                "Skeleton",
                                color = if (currentMode == "Skeleton")
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            AnimatedContent(
                targetState = isLoading.value,
                transitionSpec = {
                    (fadeIn() + slideInVertically() + scaleIn(initialScale = 0.8f))
                        .togetherWith(
                            fadeOut() + slideOutVertically() + scaleOut(
                                targetScale = 0.8f
                            )
                        )
                }
            ) { state ->
                if (state) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (currentMode == "Loading")
                            CircularProgressIndicator()
                        else Skeleton()
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = imagePainter,
                            contentDescription = "Image",
                            modifier = Modifier.size(200.dp),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

        }
    }
}


@Preview
@Composable
fun Skeleton(
    modifier: Modifier = Modifier,
) {
    // Hiệu ứng shimmer animation
    val transition = rememberInfiniteTransition(label = "shimmer")
    val shimmerTranslate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing)
        ),
        label = "shimmerAnim"
    )

    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(shimmerTranslate - 200f, shimmerTranslate - 200f),
        end = Offset(shimmerTranslate, shimmerTranslate)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.dimens.small3),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(MaterialTheme.dimens.medium1),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small2)
        ) {
            // Ảnh đại diện
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(brush)
            )

            // Nội dung text giả lập
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(18.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))

        // Box này
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(MaterialTheme.dimens.medium1))
                .background(brush)
        )
    }
}