package io.github.devcrocod.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import io.github.devcrocod.components.interaction.collectIsActiveAsState
import io.github.devcrocod.components.interaction.rememberInteractiveSource
import io.github.devcrocod.theme.*

enum class PillSize { Md, Lg }

enum class PillAccent { Magenta, Violet }

private fun PillAccent.color(): Color = when (this) {
    PillAccent.Magenta -> KotlinMagenta
    PillAccent.Violet -> KotlinViolet
}

private data class PillSpec(val font: Int, val px: Int, val py: Int)

private fun PillSize.spec(compact: Boolean): PillSpec = when (this) {
    PillSize.Md -> if (compact) PillSpec(font = 12, px = 14, py = 8) else PillSpec(font = 15, px = 22, py = 14)
    PillSize.Lg -> if (compact) PillSpec(font = 14, px = 20, py = 12) else PillSpec(font = 17, px = 28, py = 18)
}

@Composable
fun PillLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: PillSize = PillSize.Md,
    accent: PillAccent = PillAccent.Magenta,
    trailing: (@Composable () -> Unit)? = null,
) {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()
    val s = size.spec(windowSize.isCompact)
    val accentColor = accent.color()

    val borderColor by animateColorAsState(
        targetValue = if (active) accentColor else tokens.border,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "pill-border",
    )
    val bgColor by animateColorAsState(
        targetValue = if (active) accentColor.copy(alpha = 0.08f) else Color.Transparent,
        animationSpec = tween(DurState, easing = PortfolioEasing),
        label = "pill-bg",
    )

    val plexMono = plexMonoFamily()
    val labelStyle = TextStyle(
        fontFamily = plexMono,
        fontWeight = FontWeight.Normal,
        fontSize = s.font.sp,
        lineHeight = s.font.sp,
        letterSpacing = 0.05.em,
    )

    DisableSelection {
        Row(
            modifier = modifier
                // Intentional override of "No glow" rule for hero CTAs — see redesign brief.
                .accentGlow(active = active, color = accentColor)
                .clip(ShapePill)
                .background(color = bgColor, shape = ShapePill)
                .border(width = 1.dp, color = borderColor, shape = ShapePill)
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                )
                .pointerHoverIcon(PointerIcon.Hand)
                .padding(horizontal = s.px.dp, vertical = s.py.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = text.uppercase(),
                style = labelStyle,
                color = tokens.fg2,
            )
            trailing?.invoke()
        }
    }
}
