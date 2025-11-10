package com.acntem.improveuiapp.presentation.screen.opengles

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun SixStarRings(
    modifier: Modifier = Modifier,
    rings: Int = 6,
    points: Int = 5,
    baseOuterRadiusFactor: Float = 0.25f,
    gapFactor: Float = 0.1f,
) {
    // animation xoay giống GLES
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAnim"
    )

    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val minHalf = min(size.width, size.height) / 2f

        val baseOuterRadius = minHalf * baseOuterRadiusFactor
        val gap = minHalf * gapFactor

        // màu giống GLES: RGBA -> Color(red, green, blue, alpha)
        val colors = listOf(
            Color(1f, 0f, 0f, 0.5f),
            Color(0f, 1f, 0f, 0.5f),
            Color(0f, 0f, 1f, 0.5f),
            Color(1f, 1f, 0f, 0.5f),
            Color(1f, 0f, 1f, 0.5f),
            Color(0f, 1f, 1f, 0.5f)
        )

        rotate(rotation) {
            for (i in 0 until rings) {
                val outerR = baseOuterRadius + i * gap
                val innerR = outerR * 0.5f
                val localRot = i * (PI.toFloat() / (points * 6))
                val path = createStarPath(cx, cy, outerR, innerR, points, localRot)

                drawPath(
                    path = path,
                    color = colors[i % colors.size],
                    style = Fill
                )

                drawPath(
                    path = path,
                    color = Color(0f, 0f, 0f, 0.25f),
                    style = Stroke(width = (gap * 0.1f).coerceAtLeast(1f))
                )
            }
        }
    }
}

private fun createStarPath(
    cx: Float,
    cy: Float,
    outerR: Float,
    innerR: Float,
    points: Int,
    rotation: Float = 0f,
): Path {
    val path = Path()
    if (points < 2) return path

    val step = PI / points
    val startAngle = -PI / 2 + rotation

    for (i in 0 until points * 2) {
        val r = if (i % 2 == 0) outerR else innerR
        val angle = startAngle + i * step
        val x = cx + (r * cos(angle)).toFloat()
        val y = cy + (r * sin(angle)).toFloat()
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    return path
}

@Preview(showBackground = true)
@Composable
private fun PreviewSixStarRings() {
    Box(
        modifier = Modifier
            .size(320.dp)
            .padding(8.dp)
    ) {
        SixStarRings(modifier = Modifier.fillMaxSize())
    }
}
