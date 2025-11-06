package com.acntem.improveuiapp.presentation.screen.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acntem.improveuiapp.R
import com.acntem.improveuiapp.presentation.navigation.NavScreen
import com.acntem.improveuiapp.presentation.ui.theme.BitCound
import kotlinx.coroutines.delay
import kotlin.random.Random

@Preview
@Composable
fun HomeScreen(
    onNavigate: (NavScreen) -> Unit = {}
) {
    val logos = remember {
        listOf(
            R.drawable.blue_face,
            R.drawable.red_face,
            R.drawable.orange_gace,
            R.drawable.violet_face,
        )
    }
    val idx = remember {
        Random.nextInt(logos.size)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(top = 16.dp)
            .padding(horizontal = 8.dp)
    ) {
        stickyHeader {
            Box(
                modifier = Modifier.height(160.dp).fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(modifier = Modifier.align(
                    alignment = Alignment.TopStart
                ),
                    text = "ACN TEAM",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = BitCound,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary,
                    ),
                    fontSize = (MaterialTheme.typography.headlineLarge.fontSize.value + 32).sp
                )

                Image(
                    modifier = Modifier.size(120.dp).align(
                        alignment = Alignment.BottomEnd
                    ),
                    painter = painterResource(id = logos[idx]),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Logo"
                )
            }
        }

        item {
            ImproveNavCard(
                name = "UI",
                desc = "Improve UI technique",
                onClick = {
                    onNavigate(NavScreen.UiOptimizationScreen)
                },
                count = 0,
                color = MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.5f
                )
            )
        }

        item {
            ImproveNavCard(
                name = "UX",
                desc = "Improve UX technique",
                onClick = {

                },
                count = 0,
                color = MaterialTheme.colorScheme.secondary.copy(
                    alpha = 0.5f
                )
            )
        }
    }
}

@Preview
@Composable
fun ImproveNavCard(
    name: String = "UI",
    desc: String = "Improve UI technique",
    onClick: () -> Unit = {},
    count: Int = 0,
    color: Color = MaterialTheme.colorScheme.primary
) {

    val density = LocalDensity.current.density

    val rotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        rotation.animateTo(
            targetValue = 12f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
        rotation.animateTo(
            targetValue = -12f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .graphicsLayer(
                rotationX = rotation.value,
                rotationY = 0f,
                rotationZ = 0f,
                cameraDistance = 12 * density
            ).pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                            tryAwaitRelease()
                            onClick()
                    }
                )
            },
        colors = CardDefaults.cardColors().copy(
            containerColor = color
        ),
    ) {
        Box(
            Modifier.fillMaxWidth().height(180.dp).padding(16.dp)
        ) {
            Column(
                modifier = Modifier.align(
                    alignment = Alignment.TopStart
                )
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = BitCound,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Text(
                modifier = Modifier.align(
                    alignment = Alignment.BottomEnd
                ),
                text = count.toString(),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = BitCound,
                    fontWeight = FontWeight.Bold
                ),
                fontSize = (MaterialTheme.typography.headlineLarge.fontSize.value + 16).sp
            )
        }
    }
}