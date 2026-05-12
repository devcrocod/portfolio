package io.github.devcrocod.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.devcrocod.components.interaction.collectIsActiveAsState
import io.github.devcrocod.components.interaction.rememberInteractiveSource
import io.github.devcrocod.theme.DurState
import io.github.devcrocod.theme.KotlinViolet
import io.github.devcrocod.theme.PortfolioEasing
import io.github.devcrocod.theme.tokens

@Composable
fun IconButton(
    icon: Painter,
    onClick: () -> Unit,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    iconSize: Dp = 18.dp,
    accent: Color = KotlinViolet,
) {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()

    val borderColor by animateColorAsState(
        targetValue = if (active) accent else tokens.border,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "iconbtn-border",
    )
    val bgColor by animateColorAsState(
        targetValue = if (active) accent.copy(alpha = 0.08f) else Color.Transparent,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "iconbtn-bg",
    )
    val tint by animateColorAsState(
        targetValue = if (active) accent else tokens.fg2,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "iconbtn-tint",
    )
    val glow by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "iconbtn-glow",
    )

    Box(
        modifier = modifier
            .size(size)
            .drawBehind {
                if (glow <= 0f) return@drawBehind
                val r = this.size.maxDimension * 0.6f
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            accent.copy(alpha = 0.16f * glow),
                            accent.copy(alpha = 0.05f * glow),
                            Color.Transparent,
                        ),
                        center = center,
                        radius = r,
                    ),
                    center = center,
                    radius = r,
                )
            }
            .clip(CircleShape)
            .background(color = bgColor, shape = CircleShape)
            .border(width = 1.dp, color = borderColor, shape = CircleShape)
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .pointerHoverIcon(PointerIcon.Hand),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(iconSize),
        )
    }
}
