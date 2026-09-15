package org.example.project.presentation.composables

import androidx.compose.animation.core.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent

fun Modifier.customShimmer(): Modifier = composed {

    val transition = rememberInfiniteTransition(label = "shimmer")

    val translateAnim = transition.animateFloat(
        initialValue = -1000f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerAnim"
    )

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.White.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    drawWithContent {
        drawContent()

        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(translateAnim.value, translateAnim.value),
            end = Offset(
                translateAnim.value + size.width,
                translateAnim.value + size.height
            )
        )

        drawRect(brush = brush)
    }
}