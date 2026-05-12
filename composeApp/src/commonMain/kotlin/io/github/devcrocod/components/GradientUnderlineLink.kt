package io.github.devcrocod.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.github.devcrocod.components.interaction.collectIsActiveAsState
import io.github.devcrocod.components.interaction.rememberInteractiveSource
import io.github.devcrocod.theme.DurState
import io.github.devcrocod.theme.KotlinGradientColors
import io.github.devcrocod.theme.PortfolioEasing
import io.github.devcrocod.theme.tokens

@Composable
fun GradientUnderlineLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle? = null,
    color: Color = tokens.fg1,
    baseUnderlineColor: Color? = null,
    maxLines: Int = Int.MAX_VALUE,
    softWrap: Boolean = true,
) {
    val interactionSource = rememberInteractiveSource()
    val active by interactionSource.collectIsActiveAsState()
    val progress by animateFloatAsState(
        targetValue = if (active) 1f else 0f,
        animationSpec = tween(durationMillis = DurState, easing = PortfolioEasing),
        label = "gradient-underline-progress",
    )
    val resolvedStyle = style ?: LocalTextStyle.current

    DisableSelection {
        Text(
            text = text,
            style = resolvedStyle,
            color = color,
            maxLines = maxLines,
            softWrap = softWrap,
            modifier = modifier
                .hoverable(interactionSource)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                )
                .pointerHoverIcon(PointerIcon.Hand)
                .drawBehind {
                    val y = size.height - 2.dp.toPx()
                    val stroke = 1.5.dp.toPx()
                    if (baseUnderlineColor != null) {
                        drawLine(
                            color = baseUnderlineColor,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = stroke,
                        )
                    }
                    if (progress > 0f) {
                        val w = size.width * progress
                        drawLine(
                            brush = Brush.linearGradient(
                                colors = KotlinGradientColors,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, size.height),
                            ),
                            start = Offset(0f, y),
                            end = Offset(w, y),
                            strokeWidth = stroke,
                        )
                    }
                },
        )
    }
}
