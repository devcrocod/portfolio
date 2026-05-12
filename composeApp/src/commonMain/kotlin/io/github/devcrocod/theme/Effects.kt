package io.github.devcrocod.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.accentGlow(
    active: Boolean,
    color: Color,
    innerAlpha: Float = 0.16f,
    midAlpha: Float = 0.05f,
    radius: (Size) -> Float = { it.maxDimension * 0.6f },
    center: (Size) -> Offset = { Offset(it.width / 2f, it.height / 2f) },
): Modifier {
    val progress by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "accent-glow",
    )
    return this.drawBehind {
        if (progress <= 0f) return@drawBehind
        val r = radius(size)
        val c = center(size)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    color.copy(alpha = innerAlpha * progress),
                    color.copy(alpha = midAlpha * progress),
                    Color.Transparent,
                ),
                center = c,
                radius = r,
            ),
            center = c,
            radius = r,
        )
    }
}
